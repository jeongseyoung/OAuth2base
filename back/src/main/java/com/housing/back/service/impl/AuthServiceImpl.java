package com.housing.back.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.housing.back.common.CertificationNumber;
import com.housing.back.dto.req.auth.CheckCertificationRequestDto;
import com.housing.back.dto.req.auth.EmailCertificationRequestDto;
import com.housing.back.dto.req.auth.IdCheckRequestDto;
import com.housing.back.dto.req.auth.SignInRequestDto;
import com.housing.back.dto.req.auth.SignUpRequestDto;
import com.housing.back.dto.res.ResponseDto;
import com.housing.back.dto.res.auth.CheckCertificationResponseDto;
import com.housing.back.dto.res.auth.EmailCertificationResponseDto;
import com.housing.back.dto.res.auth.IdCheckResponseDto;
import com.housing.back.dto.res.auth.SignInResponseDto;
import com.housing.back.dto.res.auth.SignUpResponseDto;
import com.housing.back.entity.CertificationEntity;
import com.housing.back.entity.UserEntity;
import com.housing.back.provider.EmailProvider;
import com.housing.back.provider.JwtProvider;
import com.housing.back.repository.CertificationRepository;
import com.housing.back.repository.UserRepository;
import com.housing.back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {
            String userId = dto.getId();
            System.out.println("userId: " + userId);
            boolean hasUserId = userRepository.existsByUserId(userId);
            if (hasUserId)
                return IdCheckResponseDto.duplicatedId(); // 아이디 중복
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.DB_Error();
        }
        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            if (userRepository.existsByUserId(dto.getId()))
                return EmailCertificationResponseDto.duplicatedId();

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSuccess = emailProvider.sendCertificationMail(dto.getEmail(), certificationNumber);
            if (!isSuccess)
                EmailCertificationResponseDto.mailSendFailed();

            certificationRepository.save(new CertificationEntity(dto.getId(), dto.getEmail(), certificationNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.DB_Error();
        }
        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);

            if (certificationEntity == null)
                return CheckCertificationResponseDto.certificationFail();

            boolean isMatched = certificationEntity.getEmail().equals(email)
                    && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched)
                return CheckCertificationResponseDto.certificationFail();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.DB_Error();
        }
        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {

            String userId = dto.getId();
            boolean isExisted = userRepository.existsByUserId(userId);
            if (isExisted)
                return SignUpResponseDto.ValidationFailed();

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched = email.equals(certificationEntity.getEmail())
                    && certificationNumber.equals(certificationEntity.getCertificationNumber());
            if (!isMatched)
                return SignUpResponseDto.certificationFail();

            dto.setPassword(passwordEncoder.encode(dto.getPassword()));

            userRepository.save(new UserEntity(dto));
            certificationRepository.deleteByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.DB_Error();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;

        try {

            String userId = dto.getId();
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null || !passwordEncoder.matches(dto.getPassword(), userEntity.getPassword()))
                return SignInResponseDto.signInFail();

            token = jwtProvider.create(userEntity.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.DB_Error();
        }
        return SignInResponseDto.success(token);
    }

}
