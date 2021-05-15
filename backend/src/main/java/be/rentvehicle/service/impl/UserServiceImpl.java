package be.rentvehicle.service.impl;

import be.rentvehicle.config.TwilioConfiguration;
import be.rentvehicle.dao.AddressDAO;
import be.rentvehicle.dao.TownDAO;
import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import be.rentvehicle.security.SecurityUtils;
import be.rentvehicle.service.MailService;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import be.rentvehicle.service.mapper.UserInfoMapper;
import be.rentvehicle.service.mapper.UserMapper;
import be.rentvehicle.dao.RolesDAO;
import be.rentvehicle.dao.UserDAO;
import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final AddressDAO addressDAO;
    private final TownDAO townDAO;
    private final RolesDAO rolesDAO;
    private final MailService mailService;
    private final TwilioConfiguration twilioConfiguration;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, AddressDAO addressDAO, TownDAO townDAO,
                           RolesDAO rolesDAO, MailService mailService, TwilioConfiguration twilioConfiguration,
                           UserInfoMapper userInfoMapper, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.townDAO = townDAO;
        this.rolesDAO = rolesDAO;
        this.mailService = mailService;
        this.twilioConfiguration = twilioConfiguration;
        this.userInfoMapper = userInfoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfoDTO registerUser(@Valid UserInfoDTO userDTO) {

        userDAO.findOneByUsername(userDTO.getUsername().toLowerCase())
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyUsedException(existingUser.getUsername());
                });
        userDAO.findOneByEmailIgnoreCase(userDTO.getUserEmail())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyUsedException(existingUser.getEmail());
                });

        User user = userInfoMapper.toEntity(userDTO);
        Set<Roles> roles = new HashSet<>();
        rolesDAO.findById(RolesConstants.USER).ifPresent(roles::add);
        user.setRoles(roles);
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        // user.setVerificationPhoneCode(getVerificationPhoneCode(userDTO.getPhoneNumber()));
        user.setVerificationPhoneCode(1234567);

        String confirmationKey = UUID.randomUUID().toString();
        user.setActivated(false);
        user.setActivationKey(confirmationKey);
        user.setCreatedAt(Instant.now());
        saveTown(userDTO, user);
        user = userDAO.save(user);
        log.info("Created Information for User: {}", user);

        // mailService.sendEmailConfirmation(user.getEmail(), confirmationKey);

        return userInfoMapper.toDto(user);
    }

    private Address saveAddress(UserInfoDTO userDTO, User user) {

        Address address = new Address();
        address.setRoad(userDTO.getAddressDTO().getRoad());
        address.setHouseNumber(userDTO.getAddressDTO().getHouseNumber());
        address.setPostBox(userDTO.getAddressDTO().getPostBox());

        user.setAddress(address);
        address.setUser(user);
        addressDAO.save(address);
        return address;
    }

    private void saveTown(UserInfoDTO userDTO, User user) {

        Address address = saveAddress(userDTO, user);

        Set<Address> addresses = Set.of(address);

        final Town town = new Town();
        town.setPostcode(userDTO.getAddressDTO().getTownDTO().getPostcode());
        townDAO.findById(userDTO.getAddressDTO().getTownDTO().getPostcode())
                .ifPresentOrElse(
                        foundTown -> town.setName(foundTown.getName()),
                        () -> town.setName(userDTO.getAddressDTO().getTownDTO().getName())
                );
        town.setAddresses(addresses);
        Town savedTown = townDAO.save(town);
        address.setTown(savedTown);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findAll() {
        return userDAO.findAll()
                .stream()
                .map(userInfoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getRoles() {
        return rolesDAO.findAll()
                .stream()
                .map(Roles::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> getUserWithJwt() {
        return SecurityUtils
                .getCurrentAuthenticatedUser()
                .flatMap(userDAO::findOneByUsername)
                .map(userInfoMapper::toDto);
    }

    @Override
    public Optional<UserInfoDTO> getByUsername(String username) {
        return Optional.of(userDAO
                .findOneByUsername(username))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userInfoMapper::toDto);
    }

    @Override
    public Optional<User> updateUser(String usernameParam, String username, String email) {

        Optional<User> existingUserEmail = userDAO.findOneByEmailIgnoreCase(email);
        if (existingUserEmail.isPresent() && (!existingUserEmail.get().getUsername().equalsIgnoreCase(usernameParam))) {
            throw new EmailAlreadyUsedException(email);
        }
        Optional<User> existingUsername = userDAO.findOneByUsername(username);
        if (existingUsername.isPresent() && (!existingUsername.get().getUsername().equalsIgnoreCase(usernameParam))) {
            throw new UsernameAlreadyUsedException(username);
        }

        return Optional.of(userDAO
                .findOneByUsername(usernameParam))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setUsername(username);
                    user.setEmail(email);
                    userDAO.save(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                });
    }

    @Override
    public Optional<String> deleteUser(String username) {
        return Optional.of(userDAO
                .findOneByUsername(username))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    userDAO.delete(user);
                    return "This user '" + user.getUsername() + "' was successfully deleted !";
                });
    }

    @Transactional
    @Override
    public String bulkDeleteUsers(List<String> ids) {
        List<UUID> uuids = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        List<User> users = userDAO.findAllById(uuids);
        userDAO.deleteAll(users);
        return (users.size() == 0) ? "No users were deleted !" : "Users were successfully deleted ! N";
    }

    @Override
    public Optional<String> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return Optional.of(userDAO
                .findOneByActivationKey(key))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    userDAO.save(user);
                    log.debug("Activated user: {}", user);
                    return user.getUsername() + "'s account was successfully activated !";
                });
    }

    @Override
    public Optional<String> verifyPhoneNumber(Integer verificationCode) {
        log.debug("Verifying a phone number {}", verificationCode);
        return Optional.of(userDAO
                .findOneByVerificationPhoneCode(verificationCode))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setVerificationPhoneCode(null);
                    userDAO.save(user);
                    return user.getUsername() + "'s phone number was successfully verified !";
                });
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userDAO
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedAtBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(
                        user -> {
                            log.debug("Deleting not activated user {}", user.getUsername());
                            userDAO.delete(user);
                        }
                );
    }

    // @Scheduled(cron = "0/2 * * * * ?")
    public void findbetween() {
        userDAO
                .findAllByCreatedAtBetween(Instant.parse("2018-04-09T10:15:30.00Z"), Instant.parse("2019-11-09T10:15:30.00Z"))
                .forEach(user -> System.out.println(user.getUsername()));
    }

    @Override
    public List<UserDTO> findAllWithEagerRelationships() {
        return null;
    }

    // @Scheduled(cron = "0/2 * * * * ?")
    public void printEvery2sec() {
        // System.out.println(UUID.randomUUID());
        Instant d1 = Instant.now().minus(3, ChronoUnit.DAYS);
        Instant d3 = Instant.now().plus(3, ChronoUnit.DAYS);
        Instant d2 = Instant.parse("2021-04-09T10:15:30.00Z");
        System.out.println(d3);
        System.out.println(d2);
    }

    private int getVerificationPhoneCode(String phoneNumber) {
        SecureRandom secureRandom = new SecureRandom();
        int myCode = secureRandom.nextInt(9000000) + 1000000;

        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = "\n[RentVehicle] Your verification code is : " + myCode;
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
        log.info("Send sms {}", "sms sent with code :" + myCode);

        return myCode;
    }
}

/*
    @Scheduled(cron = "0/2 * * * * ?")
    public void printEvery2sec() {
        // System.out.println(UUID.randomUUID());
        Instant createdDate = Instant.now();
        System.out.println(createdDate);
    }
 */
