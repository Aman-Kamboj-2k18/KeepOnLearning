package annona.dao;

import java.util.List;

import annona.domain.NRIServiceBranches;



public interface NRIServiceBranchesDAO {
	
    public void save(NRIServiceBranches nRIServiceBranches);
	
	public void update(NRIServiceBranches nRIServiceBranches);
	
	public List<NRIServiceBranches> getAllNRIServiceBranches();
	
	public NRIServiceBranches getNRIServiceBranchesById(Long id);
	
	public void delete(String branchCode);
	
	public NRIServiceBranches isPresent(String branchCode);
	
	public List<NRIServiceBranches> getNRIServiceBranchesByBranchCode(String branchCode);
	
	public NRIServiceBranches getNRIServiceBranchesByBranchId(Long branchId);
	
	public List<NRIServiceBranches> getNRIServiceBranchesNotInByBranchCode(String[] branches);
	
	public NRIServiceBranches getNRIServiceBranchByBranchCode(String branchCode);

}
