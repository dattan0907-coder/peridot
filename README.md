# Peridot Scout (NeoForge 1.21.1 + GeckoLib)

- Minecraft 1.21.1, NeoForge 21.1.220, GeckoLib 4.8.4, Java 21
- Mod id: `peridot_scout`, package: `com.peridotscout`
- Entity: `peridot_scout:peridot_scout` (`/summon peridot_scout:peridot_scout`), has a spawn egg

## Assets
- `assets/peridot_scout/geo/entity/peridot_scout.geo.json`
- `assets/peridot_scout/animations/entity/peridot_scout.animation.json`
- `assets/peridot_scout/textures/entity/peridot_scout.png`  <- PLACEHOLDER (256x256), replace with your real texture

## Animations
- idle / walk / run (aggressive) / swim (in water) / death: automatic
- attack_left, attack_right, tail_whip: random on a successful hit
- roar: when it first acquires a target
- scan: randomly while idle with no target
- Not wired: hello

## Build
GitHub Actions builds on every push (`.github/workflows/build.yml`). The jar is in the run's artifacts.
Release: `git tag v1.0.0 && git push --tags`
