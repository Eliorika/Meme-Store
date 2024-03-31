package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantRole;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import dev.chipichapa.memestore.domain.model.BaseModel;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.domain.model.tenant.Tenant;
import dev.chipichapa.memestore.domain.model.tenant.TenantProfile;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.usecase.ifc.TenantUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantUseCaseImpl implements TenantUseCase {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final GalleryUseCase galleryUseCase;

    @Override
    @Transactional
    public Tenant getTenant() {
        User user = authUtils.getUserEntity();
        List<Gallery> allGalleries = galleryUseCase.getAllForUser(user);

        var publicGalleries = allGalleries.stream()
                .filter(Gallery::isPublic)
                .map(BaseModel::getId)
                .toList();

        var privateGalleries = allGalleries.stream()
                .filter(g -> (!g.isPublic()))
                .map(BaseModel::getId)
                .toList();
        return new TenantProfile(getTenantByUser(user), publicGalleries, privateGalleries);
    }

    @Override
    @Transactional
    public Tenant getTenantById(Long id) {
        User user = userService.getById(id);
        return getTenantByUser(user);
    }


    @Override
    public TenantProfile getTenantProfile(Long id) {
        User user = userService.getById(id);
        User currentUser = authUtils.getUserEntity();
        List<Gallery> allGalleries = galleryUseCase.getAllForUser(user);

        var publicGalleries = allGalleries.stream()
                .filter(Gallery::isPublic)
                .map(BaseModel::getId)
                .toList();

        var privateGalleries = allGalleries.stream()
                .filter(g -> (!g.isPublic() && g.getContributorsIds().contains(currentUser.getId())))
                .map(BaseModel::getId)
                .toList();

        return new TenantProfile(getTenantByUser(user), publicGalleries, privateGalleries);
    }

    private static Tenant getTenantByUser(User user) {
        return new Tenant(user.getId(),
                TenantRole.getAllRoles(),
                TenantType.USER,
                TenantOrigin.EXTERNAL_TELEGRAM,
                user.getDisplayName(),
                user.getUsername(),
                false);
    }

}
