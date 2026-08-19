package com.dev_spring.sentin.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.jpa.domain.Specification;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.dev_spring.sentin.dtos.UserFilterParams;
import com.dev_spring.sentin.models.SentinUser;
import com.dev_spring.sentin.specs.UserSpecifications;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserSpecificationsTest extends AbstractPostgresIntegrationContainer {

  @Autowired
  private SentinUserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();

    SentinUser user1 = new SentinUser(
        null,                      
        "Sofía",                
        "Elena",                  
        "Ramírez Mendoza",         
        "sramirez",               
        "$2a$10$e8Z4z5x8A...",     
        "sofia.ramirez@example.com",   
        LocalDate.of(1998, 5, 14),    
        "03100",                
        "5512345678",          
        "RAMS9805141H9"              
    );

    SentinUser user2 = new SentinUser(
        null,                            
        "Alejandro",           
        null,                     
        "Silva Torres",             
        "asilva",                     
        "$2a$10$9xK1w0m2B...",        
        "alejandro.silva@example.com", 
        LocalDate.of(2001, 11, 3),    
        "06700",                      
        "5587654321",               
        "SITA011103AB4"                
    );

    SentinUser user3 = new SentinUser(
        null,                          
        "Valeria",                     
        "Isabel",                    
        "Gómez Cruz",                
        "vgomez",                     
        "$2a$10$7yP3n1v9C...",          
        "valeria.gomez@example.com",   
        LocalDate.of(1995, 8, 22),      
        "04510",                    
        "5544332211",           
        "GOCV9508225T1"               
    );

    // 3. Persistimos los datos en la base de datos PostgreSQL de Testcontainers
    userRepository.saveAll(List.of(user1, user2, user3));
  }

  @Test
  void testFindByFirstName(){
    UserFilterParams filter = new UserFilterParams(
      "vAleRiA",
      null, 
      null, 
      null, 
      null, 
      null, 
      null, 
      null);

      Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
      List<SentinUser> result = userRepository.findAll(spec);
      
      assertTrue(result.size() == 1);
  }
}