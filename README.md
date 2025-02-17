## Intro
This is a guide on how to add visual measurements in to your Phoenix Tunner X generated project.

By [@YasushikoX](https://github.com/YasushikoX)

## Links to Read
**YouTube Guide:**

[![IMAGE ALT TEXT HERE](https://i.ytimg.com/an_webp/VhXCXVQJOr0/mqdefault_6s.webp?du=3000&sqp=CJf3yb0G&rs=AOn4CLA5bS7o2BrbwrgW-5y2UyJQLob70w)](https://youtu.be/VhXCXVQJOr0)
    
[Getting Started With LimeLight](https://docs.limelightvision.io/docs/docs-limelight/getting-started/FRC/wiring)  !!MUST READ!!

[Odometry Drift](https://v6.docs.ctr-electronics.com/en/stable/docs/application-notes/update-frequency-impact.html#factors-that-impact-odometry)

[LimeLight Library](https://docs.limelightvision.io/docs/docs-limelight/apis/limelight-lib)

[LimeLight Startihg With April Tags](https://docs.limelightvision.io/docs/docs-limelight/pipeline-apriltag/apriltags#quick-start-for-frc-apriltags)

[SmartDashboard](https://docs.wpilib.org/en/stable/docs/software/dashboards/smartdashboard/index.html#smartdashboard)

## Step By Step Guide
1.  [Update Your LimeLight](https://docs.limelightvision.io/docs/docs-limelight/getting-started/FRC/imaging)
2.  Set up Lime Light for MegaTag 2 in Advanced -> MegaTag Field-Space Localization. [Guide](https://docs.limelightvision.io/docs/docs-limelight/pipeline-apriltag/apriltag-robot-localization-megatag2) (Please also read lime light set up from links)
3. Update all motors etc. in Phoenix X.
4. Make sure drive train is on separte CAN bus.
5. Verify Phoenix Pro license.
6. Generate Phoenix X project. 
7. Download [LimeLight Library](https://github.com/LimelightVision/limelightlib-wpijava) and at it to your robot folder.
8. Add the following to the code:

   **At the Top:**
     ```java
     LimelightHelpers.PoseEstimate limelightMeasurement;
     private final Pigeon2 pigeon = new Pigeon2(0,"Drive Base");
     ```
   **In constructor:**
     ```java
     setVisionMeasurementStdDevs(VecBuilder.fill(.5, .5, 9999999));
     ````
   
   **In Periodic:**
     ```java
     LimelightHelpers.SetRobotOrientation("limelight", pigeon.getYaw().getValueAsDouble(), 0, 0, 0, 0, 0); 
     limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
     
     try {
         if (limelightMeasurement != null && limelightMeasurement.pose != null) {
             if (limelightMeasurement.pose.getX() != 0) {
                 addVisionMeasurement(limelightMeasurement.pose, limelightMeasurement.timestampSeconds);
                 SmartDashboard.putBoolean("LimeLight", true);
             } else{
                 SmartDashboard.putBoolean("LimeLight", false);
             }
         }
     } catch (Exception e) {
         System.err.println("An error occurred: " + e.getMessage());
         e.printStackTrace();
     }
     ```
## Tips

- Use Advantage Scope to see position
- Chief Delphi is your helper
- Check Odometry Rate and make sure its 250hz for less drift
