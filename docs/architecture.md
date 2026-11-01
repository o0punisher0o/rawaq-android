# Rawaq Architecture Guide

## Overview
Rawaq uses a pragmatic single-module architecture designed to scale cleanly into feature modules if needed.

## Key Layers
1. **`core/`**: Shared theme design system tokens (colors, shapes, typography) and common Compose UI components.
2. **`data/`**: Domain models (`Habit`, `Goal`, `DailyProgress`) and room/repository preparation.
3. **`feature/`**: Vertical feature slices containing UI screens and ViewModels (`today`, `habits`, `goals`, `settings`).
4. **`navigation/`**: Centralized routes and bottom navigation integration.

## Design Philosophy
- **Calm & Minimal**: Soft surfaces, clear typography, sage green accents (`#2E5B44`).
- **Zero Noise**: No clutter, non-intrusive empty states.
- **Universal**: Suitable for general habits as well as Islamic daily routines.
