package annona.dao;

import java.util.List;

import annona.domain.Branch;


public interface BranchDAO {

	
	public void save(Branch branch);
	
	public void update(Branch branch);
	
	public List<Branch> getAllBranches();
	
	public Branch getBranchById(Long id);
	
	public Long getCountOfBranchNameAndCode(String branchName, String branchCode);
	
	public Long getCountOfBranchName(String branchName);
	
	public Long getCountOfBranchCode(String branchCode);
	
	public List<Branch> getAllBranchesByState(String state);
	
	public Branch getBranchByBranchCode(String branchCode);
}
