package annona.webservices;

import java.util.ArrayList;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import annona.dao.CustomerDAO;
import annona.dao.EndUserDAO;
import annona.dao.FixedDepositDAO;
import annona.domain.Customer;
import annona.domain.EndUser;


@Path("/tasthana")
@Component
public class MobileAppWebservice {

	@Autowired
	EndUserDAO endUserDAO;
	
	@Autowired
	FixedDepositDAO depositDAO;
	
	@Autowired
	CustomerDAO customerDAO;
	
	/**
	 *Method to validate login of user 
	 * @param username
	 * @param password
	 * @return
	 */
	@Path("/validateLogin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateLogin(@QueryParam("user")String username, @QueryParam("pwd")String password) {
		
		List<EndUser> userList = endUserDAO.findByUsernameAndPwd(username, password);

		if(userList != null && userList.size() > 0) {
			GenericEntity<EndUser> entity = 
					new GenericEntity<EndUser>(userList.get(0)) {};
			return Response.ok(entity).build();
		}else {
			return Response.serverError().build();
		}	
	}
	
	/**
	 *Method to get list of bank employees 
	 * @return
	 */
	@Path("/bankEmpList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response bankEmpList() {
		
		List<EndUser> userList = endUserDAO.getByRoleList().getResultList();

		if(userList != null && userList.size() > 0) {
			GenericEntity<List<EndUser>> entity = 
					new GenericEntity<List<EndUser>>(userList) {};
			return Response.ok(entity).build();
		}else {
			return Response.serverError().build();
		}	
	}
	
	/**
	 *Method to get list of approval manager list
	 * @return
	 */
	@Path("/appMngList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response appMngList() {
		
		List<EndUser> userList = endUserDAO.getByApprovalMng().getResultList();

		if(userList != null && userList.size() > 0) {
			GenericEntity<List<EndUser>> entity = 
					new GenericEntity<List<EndUser>>(userList) {};
			return Response.ok(entity).build();
		}else {
			return Response.serverError().build();
		}	
	}
	
//	/**
//	 *Method to get list of deposits
//	 * @return
//	 */
//	@Path("/depositList")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response depositList() {
//		
//		List<FixedDeposit> depositList = (ArrayList<FixedDeposit>)depositDAO.findAllFDsForBank();
//
//		if(depositList != null && depositList.size() > 0) {
//			GenericEntity<List<FixedDeposit>> entity = 
//					new GenericEntity<List<FixedDeposit>>(depositList) {};
//			return Response.ok(entity).build();
//		}else {
//			return Response.serverError().build();
//		}	
//	}
	
	/**
	 *Method to get list of bank employees with approval status pending
	 * @return
	 */
	@Path("/bankEmpPendingList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response bankEmpPendingList() {
		
		List<EndUser> bankEmpList = endUserDAO.getByRole().getResultList();

		if(bankEmpList != null && bankEmpList.size() > 0) {
			GenericEntity<List<EndUser>> entity = 
					new GenericEntity<List<EndUser>>(bankEmpList) {};
			return Response.ok(entity).build();
		}else {
			return Response.serverError().build();
		}	
	}
	
	/**
	 *Method to get list of customers with approval status pending
	 * @return
	 */
	@Path("/customerPendingList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerPendingList() {
		
		List<Customer> customerList = customerDAO.getByPending().getResultList();

		if(customerList != null && customerList.size() > 0) {
			GenericEntity<List<Customer>> entity = 
					new GenericEntity<List<Customer>>(customerList) {};
			return Response.ok(entity).build();
		}else {
			return Response.serverError().build();
		}	
	}
	
//	/**
//	 *Method to get list of deposits with approval status pending
//	 * @return
//	 */
//	@Path("/depositsPendingList")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response depositsPendingList() {
//		
//		List<FixedDeposit> depositsList = depositDAO.getByPending().getResultList();
//
//		if(depositsList != null && depositsList.size() > 0) {
//			GenericEntity<List<FixedDeposit>> entity = 
//					new GenericEntity<List<FixedDeposit>>(depositsList) {};
//			return Response.ok(entity).build();
//		}else {
//			return Response.serverError().build();
//		}	
//	}
}
