package be.tsapasmi33.digitalcityairport;

import be.tsapasmi33.digitalcityairport.models.entities.Authority;
import be.tsapasmi33.digitalcityairport.models.entities.Role;
import be.tsapasmi33.digitalcityairport.models.entities.UserAccount;
import be.tsapasmi33.digitalcityairport.repositories.AuthorityRepository;
import be.tsapasmi33.digitalcityairport.repositories.RoleRepository;
import be.tsapasmi33.digitalcityairport.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DataInitializer {

    RoleRepository roleRepository;
    AuthorityRepository authorityRepository;
    UserAccountRepository userAccountRepository;
    PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        Role admin = new Role();
        admin.setName("ADMIN");
        roleRepository.save(admin);

        Role passenger = new Role();
        passenger.setName("PASSENGER");
        roleRepository.save(passenger);

        Role pilot = new Role();
        pilot.setName("PILOT");
        roleRepository.save(pilot);

        Authority flightModifier = new Authority();
        flightModifier.setAuthority("FLIGHT_MODIFIER");
        authorityRepository.save(flightModifier);

        UserAccount user1 = new UserAccount();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("1"));
        user1.setRoles(List.of(admin));
        userAccountRepository.save(user1);

        UserAccount user2 = new UserAccount();
        user2.setUsername("adminPlus");
        user2.setPassword(encoder.encode("2"));
        user2.setRoles(List.of(admin));
        user2.setAuthorities(List.of(flightModifier));
        userAccountRepository.save(user2);

        UserAccount user3 = new UserAccount();
        user3.setUsername("passenger");
        user3.setPassword(encoder.encode("3"));
        user3.setRoles(List.of(passenger));
        userAccountRepository.save(user3);

        UserAccount user4 = new UserAccount();
        user4.setUsername("pilot");
        user4.setPassword(encoder.encode("4"));
        user4.setRoles(List.of(pilot));
        userAccountRepository.save(user4);
    }
}
