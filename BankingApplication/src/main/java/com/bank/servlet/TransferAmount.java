package com.bank.servlet;

import java.io.IOException;

import com.bank.dao.CustomerDAO;
import com.bank.dao.CustomerDAOimp;
import com.bank.dao.TransactionDAO;
import com.bank.dao.TransactionDAOimp;
import com.bank.dto.Customer;
import com.bank.dto.Transaction;
import com.bank.dto.TransactionId;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/transferAmount")
public class TransferAmount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
    	HttpSession session=req.getSession(false);
    	CustomerDAO cdao=new CustomerDAOimp();
		Transaction t1=new Transaction();
		Transaction t2=new Transaction();
		TransactionDAO tdao=new TransactionDAOimp();
		double amount=Double.parseDouble(req.getParameter("amount"));
		long reci_accNo=Long.parseLong(req.getParameter("recipient"));
		int pin=Integer.parseInt(req.getParameter("pin"));
		Customer receiver=cdao.getCustomer(reci_accNo);
		Customer c=(Customer)session.getAttribute("customer");
		if(c.getAccNo()!=receiver.getAccNo() && c.getBal()>0 && c.getBal()>=0 && amount>0&&amount<c.getBal())
		{
			
			if(c.getPin()==pin)
			{
				c.setBal(c.getBal()-amount);
				boolean c_res=cdao.updateCustomer(c);
				if(c_res)
				{
					t1=new Transaction();
					t1.setTransactionId(TransactionId.generateTransactionId());
					t1.setUser(c.getAccNo());
					t1.setRec_acc(receiver.getAccNo());
					t1.setTransation("DEBITED");
					t1.setAmount(amount);
					t1.setBalance(c.getBal());
					boolean res1=tdao.insertTransaction(t1);
				}
				receiver.setBal(receiver.getBal()+amount);
				boolean recv_res=cdao.updateCustomer(receiver);
				if(recv_res)
				{
					t2=new Transaction();
					t2.setTransactionId(t1.getTransactionId());
					t2.setUser(receiver.getAccNo());
					t2.setRec_acc(c.getAccNo());
					t2.setTransation("CREDITED");
					t2.setAmount(amount);
					t2.setBalance(receiver.getBal());
					boolean res1=tdao.insertTransaction(t2);
				}
				
				if(c_res&&recv_res)
				{
					session.setAttribute("recvacc", t2);
					req.setAttribute("transfer","Transaction successfull");
					RequestDispatcher rd=req.getRequestDispatcher("transfer.jsp");
					rd.forward(req, resp);
				}
				else
				{
					req.setAttribute("failure","Transaction failed");
					RequestDispatcher rd=req.getRequestDispatcher("transfer.jsp");
					rd.forward(req, resp);
				}
			}
		}
		else
		{
			session.setAttribute("failure","Transaction failed");
			RequestDispatcher rd=req.getRequestDispatcher("transfer.jsp");
			rd.forward(req, resp);		}
    }
}
