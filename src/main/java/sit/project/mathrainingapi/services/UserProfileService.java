package sit.project.mathrainingapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sit.project.mathrainingapi.dtos.PaginationResponseDTO;
import sit.project.mathrainingapi.entities.UserProfile;
import sit.project.mathrainingapi.exceptions.BadRequestException;
import sit.project.mathrainingapi.exceptions.NotFoundException;
import sit.project.mathrainingapi.repositories.UserProfileRepository;
import sit.project.mathrainingapi.utils.ListMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper mapper;

    public UserProfile getUserProfileById(String id) {
        return userProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("UserProfile not found"));
    }

    public PaginationResponseDTO getAllUserProfile(String sortBy, Integer page, Integer per_page, String sortDirection) {
        try {
            sortBy = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id";
            sortDirection = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? "desc" : "asc";
            page = (page != null && page > 0) ? page : 1;
            per_page = (per_page != null && per_page > 0) ? per_page : 10;

            Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

            Pageable pageable = PageRequest.of(page - 1, per_page, sort);

            List<UserProfile> data = userProfileRepository.findUserProfilesPageableAndSort(sortBy, pageable);

            long total = userProfileRepository.countUserProfiles();

            int pages = (int) Math.ceil((double) total / per_page);
            int first = 1;
            int last = pages;
            int prev = page > 1 ? page - 1 : 1;
            int next = page < pages ? page + 1 : pages;

            return new PaginationResponseDTO(first, prev, next, last, pages, (int) total, data);

        } catch (Exception e) {
            throw new BadRequestException("Invalid sortBy property: " + sortBy);
        }
    }

    public List<UserProfile> getAllUserProfile() {
        return userProfileRepository.findAll();
    }

    public UserProfile createUserProfile(UserProfile userProfile) {
        try {
            return userProfileRepository.save(userProfile);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public UserProfile updateUserProfile(String id, UserProfile userProfile) {
        UserProfile userProfileToUpdate = userProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("UserProfile not found"));
        try {
            if (userProfile.getUserName() != null) {
                userProfileToUpdate.setUserName(userProfile.getUserName());
            }
            if (userProfile.getDescrition() != null) {
                userProfileToUpdate.setDescrition(userProfile.getDescrition());
            }
            if (userProfile.getRankingHighScore() != null) {
                userProfileToUpdate.setRankingHighScore(userProfile.getRankingHighScore());
            }
            if (userProfile.getCompleteAchievement() != null) {
                userProfileToUpdate.setCompleteAchievement(userProfile.getCompleteAchievement());
            }
            if (userProfile.getProfileConfig() != null) {
                userProfileToUpdate.setProfileConfig(userProfile.getProfileConfig());
            }
            if (userProfile.getTotalScore() != null) {
                userProfileToUpdate.setTotalScore(userProfile.getTotalScore());
            }
            if (userProfile.getTotalTimePlay() != null) {
                userProfileToUpdate.setTotalTimePlay(userProfile.getTotalTimePlay());
            }
            if (userProfile.getCustomLevel() != null) {
                userProfileToUpdate.setCustomLevel(userProfile.getCustomLevel());
            }

            return userProfileRepository.save(userProfileToUpdate);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }


    public void deleteUserProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("UserProfile not found"));
        try {
            userProfileRepository.delete(userProfile);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
