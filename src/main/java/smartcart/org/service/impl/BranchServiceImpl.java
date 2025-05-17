package smartcart.org.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartcart.org.dto.BranchDto;
import smartcart.org.entity.Branch;
import smartcart.org.exception.ResourceNotFoundException;
import smartcart.org.repository.BranchRepository;
import smartcart.org.service.BranchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    String branchNotFoundWithId = "Branch not found with id: ";

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = modelMapper.map(branchDto, Branch.class);
        Branch savedBranch = branchRepository.save(branch);
        return modelMapper.map(savedBranch, BranchDto.class);
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(branchNotFoundWithId + id));
        return modelMapper.map(branch, BranchDto.class);
    }

    @Override
    public List<BranchDto> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream()
                .map(branch -> modelMapper.map(branch, BranchDto.class))
                .toList();
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(branchNotFoundWithId + id));

        existingBranch.setName(branchDto.getName());
        existingBranch.setLocation(branchDto.getLocation());

        Branch updatedBranch = branchRepository.save(existingBranch);
        return modelMapper.map(updatedBranch, BranchDto.class);
    }

    @Override
    public Boolean deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new ResourceNotFoundException(branchNotFoundWithId + id);
        }
        branchRepository.deleteById(id);
        return !branchRepository.existsById(id);
    }
}
