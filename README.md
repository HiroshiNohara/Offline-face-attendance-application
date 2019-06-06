# Offline face attendance application

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/icon.png" alt="Sample"  width="100" height="100"></p>


This application can help you streamline your employee attendance process by simply running it on an Android device that supports front camera.

## Interface

After opening this application, you will see the following interfaces:

### Main interface

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot0.jpg" alt="Sample"  width="360" height="640"></p>

### Attendance

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot1.jpg" alt="Sample"  width="360" height="640">
</p>

### Employee information registration

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot2.jpg" alt="Sample"  width="360" height="640">
  <img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot3.jpg" alt="Sample"  width="360" height="640">
</p>

### Employee attendance days record

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot9.jpg" alt="Sample"  width="360" height="640">
</p>

### Settings

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot7.jpg" alt="Sample"  width="360" height="640">
</p>

### Shortcuts

<p align="left">
	<img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot8.jpg" alt="Sample"  width="360" height="640">
</p>

**In addition, if you can find the egg of this application, you will get employee information management functions additionally:**

### Management

<table>
  <tr>
    <td>Employee information details display:</td>
    <td>Delete retired employee information<br>(can be withdrawn):</td>
    <td>Modify employee information:</td>
  </tr>
  <tr>
    <td><img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot4.jpg" alt="Sample"  width="240" height="427"></td>
    <td><img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot5.jpg" alt="Sample"  width="240" height="427"></td>
    <td><img src="https://github.com/HiroshiNohara/Offline-face-attendance-application/blob/master/Screenshots/Screenshot6.jpg" alt="Sample"  width="240" height="427"></td>
  </tr>
</table>

## Application function

### All features of this application do not need to connect to the server. After opening this application, you will be able to use the following features:

- Employee attendance based on face detection
- Information registration and face entry for new employees
- Set the attendance time interval
- Verify the fingerprint to ensure the process is self-operating
- Automatically exclude the same employee from checking in repeatedly
- Fuzzy search of employee information based on a job number or name
- View employees attendance history
- Thorough Simplified Chinese, traditional Chinese and English support
- Quick entry(App shortcuts)
- More……

### After opening the application egg, you will get the following additional features:

- Delete employee information in a single/bulk
- Deleted employee information can be withdrawn in a short time
- Modify the employee information that has been entered

## Precautions

- All major functions can be switched by swiping the pop-up side-slip menu to the right.
- This application will only apply for **Camera** permissions on your device. Individual ROMs will prompt the application to request additional permissions, you can choose to ignore or deny these permissions.
- You can clear the employee's attendance mark for the day at any time via `Settings`-`Reset attendance`, but I don't recommend you to do this because the application will do this for you automatically.
- The default operation after fingerprint verification is the user's own operation, so please clear the activity of **FaceAttendance** in the background after exiting.
- This application only passes the real machine test on Huawei Mate 9. For some reasons, the face detection frame of the mobile phone device of other models will be offset, but this may not affect the normal use.
- The network permission required for this application is only for displaying and accessing the GitHub address of the open source framework authors, without any upload or download behavior within the application.

## Change logs

### 1.3.1

- Support for automatic reset of employee's attendance mark
- Fix known issues

### 1.2.1

- Add search function
- Add the view of employee attendance history
- Restrict employee repeat attendance
- Simplify some operational processes
- Fix known issues

### 1.1.0

- Remove the administrator login function
- Fix the problem that the application control is incomplete under the device landscape
- Support for multi-language switching
- Interface UI logic adjustment
- Individual interface increases right slide exit gesture
- Interface operation animation adjustment
- About the application interface, you can view all the open source frameworks used by this application, thanks to the open source spirit of the framework authors
- Strengthen the logical judgment when setting the attendance time interval
- Fix known issues

### 1.0.0

- Release the application

## Acknowledgement

- Face recognition algorithm used in this application is from a paper published by T.Ojala et al. in 1996, see **Reference**.

- Simultaneously, this application uses several open source frameworks in the development process, which can be viewed in the application via `Settings`-`Application version`-`Open source related`, thanks to the open source spirit of these framework authors.

## Reference

- T. Ojala, M. Pietikäinen, and D. Harwood (1996), "A Comparative Study of Texture Measures with Classification Based on Feature Distributions", Pattern Recognition, vol. 29, pp. 51-59.
