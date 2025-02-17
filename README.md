## Intro
This is a guide on how to add visual measurments in to your Phoenix Tunner X generated project.
By Yasushiko(Jake)

## Links to Read
**YouTube Guide:**

[![IMAGE ALT TEXT HERE](https://i.ytimg.com/an_webp/VhXCXVQJOr0/mqdefault_6s.webp?du=3000&sqp=CJf3yb0G&rs=AOn4CLA5bS7o2BrbwrgW-5y2UyJQLob70w)](https://youtu.be/VhXCXVQJOr0)
    
[Getting Started With LimeLight](https://docs.limelightvision.io/docs/docs-limelight/getting-started/FRC/wiring)

[Odometry Drift](https://v6.docs.ctr-electronics.com/en/stable/docs/application-notes/update-frequency-impact.html#factors-that-impact-odometry)

[LimelIght Programming](https://docs.limelightvision.io/docs/docs-limelight/apis/limelight-lib)

[SmartDashboard](https://docs.wpilib.org/en/stable/docs/software/dashboards/smartdashboard/index.html#smartdashboard)

## Step By Step Guide
1.  [Update Your LimeLight](https://docs.limelightvision.io/docs/docs-limelight/getting-started/FRC/imaging)
2. Update all motors etc. in Phoenix X.
3. Make sure drive train is on separte CAN bus.
4. Verify Phoenix Pro license.
5. Generate Phoenix X project. 
6. Download [LimeLight Library](https://github.com/LimelightVision/limelightlib-wpijava) and at it to your robot folder.
7. Add the following to the code:

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
