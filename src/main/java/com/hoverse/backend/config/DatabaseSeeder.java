package com.hoverse.backend.config;

import com.hoverse.backend.entity.Category;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.repository.CategoryRepository;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count()==0){
            User admin = User.builder()
                    .username("hoangadmin")
                    .email("admin@hoverse.com")
                    .password("123456")
                    .role(Role.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();

            User genzUser = User.builder()
                    .username("genz_explorer")
                    .email("user@hoverse.com")
                    .password("123456")
                    .role(Role.USER)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.saveAll(List.of(admin,genzUser));
            System.out.println("Created data for Users table!");
        }

        if (categoryRepository.count() == 0) {
            Category cafe = Category.builder().name("Cà phê Chill").slug("ca-phe-chill").isActive(true).build();
            Category nahu = Category.builder().name("Quán Nhậu").slug("quan-nhau").isActive(true).build();
            Category boardgame = Category.builder().name("Board Game").slug("board-game").isActive(true).build();

            categoryRepository.saveAll(List.of(cafe, nahu, boardgame));
            System.out.println("Created data for Categories table!");
        }

        if (placeRepository.count() == 0) {
            Category cafe = categoryRepository.findAll().get(0);
            User creator = userRepository.findAll().get(1);

            Place place1 = Place.builder()
                    .title("Cà phê Vợt Phan Đình Phùng")
                    .description("Quán cafe vợt lâu đời, không gian vỉa hè siêu chill cho Gen Z ngồi chém gió xuyên đêm.")
                    .address("330 Phan Đình Phùng, Phường 1, Phú Nhuận, TP.HCM")
                    .normalizedAddress("330phandinhphungphuong1phunhuan")
                    .latitude(new BigDecimal("10.79630000"))
                    .longitude(new BigDecimal("106.69120000"))
                    .category(cafe)
                    .user(creator)
                    .status(PlaceStatus.APPROVED)
                    .build();

            placeRepository.save(place1);
            System.out.println("Created data for Places table!");
        }
    }
}
