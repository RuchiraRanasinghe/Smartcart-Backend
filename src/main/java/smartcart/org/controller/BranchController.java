package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.BranchDto;
import smartcart.org.response.ApiResponse;
import smartcart.org.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
@Slf4j
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<ApiResponse<BranchDto>> persist(@RequestBody BranchDto branchDto) {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, HttpStatus.CREATED.value(), createdBranch));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchDto>> getById(@PathVariable("id") Long id) {
        BranchDto branch = branchService.getBranchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, HttpStatus.OK.value(), branch));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BranchDto>>> getAll() {
        List<BranchDto> branches = branchService.getAllBranches();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, HttpStatus.OK.value(), branches));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchDto>> update(@PathVariable("id") Long id, @RequestBody BranchDto branchDto) {
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK.value(), updatedBranch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") Long id) {
        Boolean deleted = branchService.deleteBranch(id);
        if (Boolean.TRUE.equals(deleted)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,HttpStatus.CREATED.value(), null,"Branch deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(true,HttpStatus.NOT_FOUND.value(), null,"Branch Not Found"));
        }
    }
}
