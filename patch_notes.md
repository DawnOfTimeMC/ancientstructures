## 🏛️ Ancient Structures Forge/Fabric 1.20.1 [v0.0.14]

- Reworked advancement system with custom Java trigger (fixes all advancements triggering simultaneously on world load)
- New advancement tree: node_explorer as a 22/22 master counter, 5 cultural sub-advancements (japanese, roman, germanic, maya, ruins)
- Updated Readme
- Fixed lone_priest and windmill structures not spawning (corrupted NBT re-exported)
- Fixed fabric mod not loading due to incorrect id in fabric.mod.json

### 🔧 New systems created for this version

- `StructureDiscoveryTrigger` — custom criterion trigger `ancientstructures:structure_discovery` replacing the broken vanilla LocationPredicate system
- `CommonStructureChecker` — shared Forge/Fabric logic using `StructureManager.getAllStructuresAt()` to detect structures at runtime
- `ForgeEventHandler` — server-side tick event handler (every 20 ticks) firing the structure discovery trigger

If you find any bugs, please report them on the issue tracker!

**:orange_heart: Support the project on Patreon:** https://urlr.me/1RLyj
