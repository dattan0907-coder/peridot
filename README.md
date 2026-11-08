# Peridot Scout (NeoForge 1.21.1 + GeckoLib)

- Minecraft 1.21.1, NeoForge 21.1.220, GeckoLib 4.8.4, Java 21
- Mod id: `peridot_scout`, package: `com.peridotscout`
- Entity: `peridot_scout:peridot_scout` (`/summon peridot_scout:peridot_scout`), has a spawn egg

## Assets
- `assets/peridot_scout/geo/entity/peridot_scout.geo.json`
- `assets/peridot_scout/animations/entity/peridot_scout.animation.json`
- `assets/peridot_scout/textures/entity/peridot_scout.png`  (256x256)
- `assets/peridot_scout/textures/entity/peridot_scout_dead.png`  <- swapped in while the death animation plays

## Animations
- idle / walk / run (aggressive) / swim (in water) / death: automatic
- attack_left, attack_right, tail_whip: random on a successful hit
- roar: when it first acquires a target
- scan: randomly while idle with no target
- Not wired: hello

## Build
GitHub Actions builds on every push (`.github/workflows/build.yml`). The jar is in the run's artifacts.
Release: `git tag v1.0.0 && git push --tags`

## Sounds
Generated placeholder sounds (mono OGG Vorbis) in `assets/peridot_scout/sounds/entity/`,
mapped in `assets/peridot_scout/sounds.json`: ambient (x2), hurt (x2), death, step (x2), roar, scan.
Replace any `.ogg` with your own file of the same name to change a sound.
