package com.jf.logistics.controller;


import com.jf.logistics.model.entity.Company;
import com.jf.logistics.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistics/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public Company save(@RequestBody Company company) throws Exception {
        return companyService.save(company);
    }

    @PostMapping("/selectCompanyList")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public Map<String,Object> selectCompanyList(String companyName,int page,int pageSize){
        return companyService.findCompanyByCompanyNameLike(companyName,page,pageSize);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public void delete(String id){
        companyService.delete(id);
    }

    @GetMapping("")
    public List<Company> findAll(){
        return companyService.findAll();
    }

}
