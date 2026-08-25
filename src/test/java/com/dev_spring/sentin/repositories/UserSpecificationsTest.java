package com.dev_spring.sentin.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    // Edad aprox a 2026: 28 años
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

    // Edad aprox a 2026: 24 años
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

    // Edad aprox a 2026: 31 años
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

    userRepository.saveAll(List.of(user1, user2, user3));
  }

  @Test
  @DisplayName("Filter by given name (givenName)")
  void testFindByGivenName() {
    UserFilterParams filter = new UserFilterParams("sOfia", null, null, null, null, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("sramirez"), "Expected user 'sramirez'");
  }

  @Test
  @DisplayName("Filter by middle name (middleName)")
  void testFindByMiddleName() {
    UserFilterParams filter = new UserFilterParams(null, "elena", null, null, null, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getGivenName().equals("Sofía"), "Expected givenName 'Sofía'");
  }

  @Test
  @DisplayName("Filter by family name (familyName)")
  void testFindByFamilyName() {
    UserFilterParams filter = new UserFilterParams(null, null, "gomez", null, null, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("vgomez"), "Expected user 'vgomez'");
  }

  @Test
  @DisplayName("Filter by username (username)")
  void testFindByUsername() {
    UserFilterParams filter = new UserFilterParams(null, null, null, "asilva", null, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getGivenName().equals("Alejandro"), "Expected user 'Alejandro'");
  }

  @Test
  @DisplayName("Filter by minimum age (minAge)")
  void testFindByMinAge() {
    // Usuarios con al menos 30 años (Valeria: 31 años)
    UserFilterParams filter = new UserFilterParams(null, null, null, null, 30, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("vgomez"), "Expected user 'vgomez'");
  }

  @Test
  @DisplayName("Filter by maximum age (maxAge)")
  void testFindByMaxAge() {
    // Usuarios con 25 años o menos (Alejandro: 24 años)
    UserFilterParams filter = new UserFilterParams(null, null, null, null, null, 25, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("asilva"), "Expected user 'asilva'");
  }

  @Test
  @DisplayName("Filter by postal code (postalCode)")
  void testFindByPostalCode() {
    UserFilterParams filter = new UserFilterParams(null, null, null, null, null, null, "03100", null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("sramirez"), "Expected user 'sramirez'");
  }

  @Test
  @DisplayName("Filter by RFC")
  void testFindByRfc() {
    UserFilterParams filter = new UserFilterParams(null, null, null, null, null, null, null, "SITA011103AB4");

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 1, "Expected 1 user, but found " + result.size());
    assertTrue(result.get(0).getUsername().equals("asilva"), "Expected user 'asilva'");
  }

  @Test
  @DisplayName("Filter when all parameters are null or empty should return all records")
  void testFindAllWhenFilterIsEmpty() {
    UserFilterParams filter = new UserFilterParams(null, "", "   ", null, null, null, null, null);

    Specification<SentinUser> spec = UserSpecifications.getQueryWithFilters(filter);
    List<SentinUser> result = userRepository.findAll(spec);

    assertTrue(result.size() == 3, "Expected 3 users, but found " + result.size());
  }
}