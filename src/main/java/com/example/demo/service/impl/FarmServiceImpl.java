@Service
@Transactional
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public FarmServiceImpl(FarmRepository farmRepository, UserRepository userRepository) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Farm createFarm(Long ownerId, Farm farm) {

        // 1️⃣ Validate soil pH
        if (farm.getSoilPH() < 3.0 || farm.getSoilPH() > 10.0) {
            throw new IllegalArgumentException("Invalid pH range");
        }

        // 2️⃣ Validate season
        if (!ValidationUtil.validSeason(farm.getSeason())) {
            throw new RuntimeException("Invalid season");
        }

        // 3️⃣ Fetch owner from DB (VERY IMPORTANT)
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4️⃣ Attach owner to farm
        farm.setOwner(owner);

        // 5️⃣ Save farm
        return farmRepository.save(farm);
    }

    @Override
    public List<Farm> getFarmsByOwner(Long ownerId) {
        return farmRepository.findByOwnerId(ownerId);
    }

    @Override
    public Farm getFarmById(Long farmId) {
        return farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
    }
}
