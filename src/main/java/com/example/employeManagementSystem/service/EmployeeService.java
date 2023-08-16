package com.example.employeManagementSystem.service;

import com.example.employeManagementSystem.dto.EmployeeDto;
import com.example.employeManagementSystem.entity.Employee;
import com.example.employeManagementSystem.repo.EmployeeRepo;
import com.example.employeManagementSystem.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;



    public String saveEmployee(EmployeeDto employeeDto){
        if (employeeRepo.existsById(employeeDto.getEmpId())){
           return VarList.RSP_DUPLICATED;
        }else{
           employeeRepo.save(modelMapper.map(employeeDto, Employee.class));
           return VarList.RSP_SUCCESS;
        }
    }

    public String updateEmployee(EmployeeDto employeeDto){
        if (employeeRepo.existsById(employeeDto.getEmpId())){
            employeeRepo.save(modelMapper.map(employeeDto, Employee.class));
            return VarList.RSP_SUCCESS;
        } else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<EmployeeDto> getAllEmployee(){
        List<Employee> employeeList = employeeRepo.findAll();
        return modelMapper.map(employeeList, new TypeToken<ArrayList<EmployeeDto>>(){}.getType());
    }

    public EmployeeDto searchEmployee(int empId){
        if (employeeRepo.existsById(empId)){
            Employee employee = employeeRepo.findById(empId).orElse(null);
            return modelMapper.map(employee, EmployeeDto.class);
        }else{
            return null;
        }
    }

    public String deleteEmployee(int empId){
       if (employeeRepo.existsById(empId)){
           employeeRepo.deleteById(empId);
           return VarList.RSP_SUCCESS;
       } else {
           return VarList.RSP_NO_DATA_FOUND;
       }
    }



}
