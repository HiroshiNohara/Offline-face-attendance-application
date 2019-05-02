# Offline face attendance application
## Introduction

This is an offline face attendance application. The required permissions are only the Camera privileges of your device (some ROMs will additionally display “Read Application List Permissions”, but I have not and have never intended to use this permission). In addition, the application is compatible with all Android versions between version L and P.

There are two basic functions for registration and recognition in the application. Of course, you can also get a flexible information management function by opening the application egg.

The face recognition algorithm is from a paper published by T.Ojala et al. in 1996, see **Reference**.

**(Note: This application only passes the real machine test on Huawei Mate 9. For some reasons, the face detection frame of the mobile phone device of other models will be offset, but this may not affect the normal use.)**

## Reference

- T. Ojala, M. Pietikäinen, and D. Harwood (1996), "A Comparative Study of Texture Measures with Classification Based on Feature Distributions", Pattern Recognition, vol. 29, pp. 51-59.