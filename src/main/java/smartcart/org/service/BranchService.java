package smartcart.org.service;

import smartcart.org.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branch);

    BranchDto getBranchById(Long id);

    List<BranchDto> getAllBranches();

    BranchDto updateBranch(Long id, BranchDto branch);

    Boolean deleteBranch(Long id);
}
