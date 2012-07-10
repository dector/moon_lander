Moon Lander
===========
Moon Lander is space game. You need to land your spaceship onto the special platform.

Controls
========

Use Up/Left/Right keys to control your rocket.
M - mute sound.
P - pause.
Space - play next level (if landed).
E - edit current level
Shift+E - edit empty level

in editor 
---------

Escape - exit
M - mute game
R - test level
S - save level (will be saved to saved_levels/ dir)
L - load level

1 - pointer tool (do nothing)
2 - drawer tool (draw map) *
3 - place flag
4 - place landing platform **
5 - place rocket ***

* - Click at left screen bound (where circle near cursor drawn);
click on the map to place points (now next point must have greater x coordinate, than prev. point);
click at right screen bound (circle is drawn too) to finish map drawing.
Use additional keys with drawer tool:

Backspace - delete last point

** - Use additional keys with landing tool:
Mouse wheel up/down - make platform wider or narrower (for 10 pixels).
Shift + wheel up/down - change platform size (with step 50 pixels).
Ctrl + wheel up/down - change platform size (with step 1 pixels).

*** - Use additional keys with rocket tool:
Mouse wheel up/down - rotate rocket angle (for 1 degree).
Shift + wheel up/down - rotate rocket angle (for 10 degrees).
Ctrl + wheel up/down - rotate rocket angle (for 0.1 degree).
Up - set rocket direction angle 90
Down - set rocket direction angle 270
Left - set rocket direction angle 180
Right - set rocket direction angle 0
