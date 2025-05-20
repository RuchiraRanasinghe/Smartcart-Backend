package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.BranchDto;
import smartcart.org.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
@Slf4j
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDto> persist(@RequestBody BranchDto branchDto) {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getById(@PathVariable("id") Long id) {
        BranchDto branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAll() {
        List<BranchDto> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> update(@PathVariable("id") Long id, @RequestBody BranchDto branchDto) {
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        Boolean deleted = branchService.deleteBranch(id);
        if (Boolean.TRUE.equals(deleted)) {
            return ResponseEntity.status(201).body("Branch deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Branch not found");
        }
    }
}
