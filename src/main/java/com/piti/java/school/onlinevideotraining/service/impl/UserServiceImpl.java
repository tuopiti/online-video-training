package com.piti.java.school.onlinevideotraining.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piti.java.school.onlinevideotraining.config.security.AuthUser;
import com.piti.java.school.onlinevideotraining.email.EmailSender;
import com.piti.java.school.onlinevideotraining.exception.ApiException;
import com.piti.java.school.onlinevideotraining.mapper.UserMapper;
import com.piti.java.school.onlinevideotraining.model.ConfirmationTokenEmail;
import com.piti.java.school.onlinevideotraining.model.Role;
import com.piti.java.school.onlinevideotraining.model.User;
import com.piti.java.school.onlinevideotraining.repository.ConfirmationTokenEmailRepository;
import com.piti.java.school.onlinevideotraining.repository.RoleRepository;
import com.piti.java.school.onlinevideotraining.repository.UserRepository;
import com.piti.java.school.onlinevideotraining.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	private final ConfirmationTokenEmailRepository confirmationTokenEmailRepository;	
	private final EmailSender emailSender;
	
	@Override
	public Optional<AuthUser> loadUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST, "Email not found"));
		AuthUser authUser = UserMapper.INSTANCE.toAuthUser(user);
		
		Set<SimpleGrantedAuthority> authorities = user.getRoles()
				.stream()
				.flatMap(role -> toStreamOfSimpleGrantedAuthority(role))
				.collect(Collectors.toSet());
		authUser.setGrantedAuthorities(authorities);
	    return Optional.ofNullable(authUser);
	}
	
	private Stream<SimpleGrantedAuthority> toStreamOfSimpleGrantedAuthority(Role role){
		Set<SimpleGrantedAuthority> permissions = role.getPermissions().stream()
				.map(p -> new SimpleGrantedAuthority(p.getName()))
				.collect(Collectors.toSet());
			permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			return permissions.stream();
	}

	@Override
	public String registerUser(User user) {
		
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
				             
		if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }
		
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		Role role = roleRepository.findByName("SUBSCRIBER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Set.of(role));      
		userRepository.save(user);
		
		
		String token = UUID.randomUUID().toString();
		ConfirmationTokenEmail confirmationTokenEmail = new ConfirmationTokenEmail(
				token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
		
		
		confirmationTokenEmailRepository.save(confirmationTokenEmail);
		String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
		
		 emailSender.send(
				    user.getEmail(),
	                buildEmail(user.getFirstName(), link));
		 
	    return token;
	}

	@Override
	public String confirmTokenEmail(String token) {
		ConfirmationTokenEmail confirmationToken = confirmationTokenEmailRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
		 
		 if (confirmationToken.getConfirmedAt() != null) {
	         throw new IllegalStateException("email already confirmed");
	     }
		 
		 LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        userRepository.enableUser(confirmationToken.getUser().getEmail());         
        confirmationTokenEmailRepository.updateConfirmedAt(token);
        return "confirmed";
	}
	
	
	 private String buildEmail(String name, String link) {
	        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
	                "\n" +
	                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
	                "\n" +
	                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
	                "    <tbody><tr>\n" +
	                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
	                "        \n" +
	                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
	                "          <tbody><tr>\n" +
	                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
	                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
	                "                  <tbody><tr>\n" +
	                "                    <td style=\"padding-left:10px\">\n" +
	                "                  \n" +
	                "                    </td>\n" +
	                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
	                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
	                "                    </td>\n" +
	                "                  </tr>\n" +
	                "                </tbody></table>\n" +
	                "              </a>\n" +
	                "            </td>\n" +
	                "          </tr>\n" +
	                "        </tbody></table>\n" +
	                "        \n" +
	                "      </td>\n" +
	                "    </tr>\n" +
	                "  </tbody></table>\n" +
	                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
	                "    <tbody><tr>\n" +
	                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
	                "      <td>\n" +
	                "        \n" +
	                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
	                "                  <tbody><tr>\n" +
	                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
	                "                  </tr>\n" +
	                "                </tbody></table>\n" +
	                "        \n" +
	                "      </td>\n" +
	                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
	                "    </tr>\n" +
	                "  </tbody></table>\n" +
	                "\n" +
	                "\n" +
	                "\n" +
	                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
	                "    <tbody><tr>\n" +
	                "      <td height=\"30\"><br></td>\n" +
	                "    </tr>\n" +
	                "    <tr>\n" +
	                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
	                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
	                "        \n" +
	                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
	                "        \n" +
	                "      </td>\n" +
	                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
	                "    </tr>\n" +
	                "    <tr>\n" +
	                "      <td height=\"30\"><br></td>\n" +
	                "    </tr>\n" +
	                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
	                "\n" +
	                "</div></div>";
	    }
	 
	 
	private Role checkRoleExist(){
        Role role = new Role();
        role.setName("SUBSCRIBER");
        return roleRepository.save(role);
    }

}
