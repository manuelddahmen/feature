
# Feature

https://drive.google.com/folderview?id=10CXWU2JPVsNMbHrAssxN23KzizzNkvHg
Documentation utilisée.

## Filtres (kernels) d'images
Filtres : flou Gauss, 
Gradient X, Y, outter dot product. 
SobelX, SobelY. localextrema, histogramme des densites,
harris matrix c(x,y)
## Formats et classes d'images. 
- BufferedImage Int Rgb (sans rgb)
- format jpg en sortie
- PixM components matrix of pictures. 
color component based. 
- M3 components 
  + matrix (columnsIn, linesIn) internal
  matrix at x, y
- conversion to BufferedImage, minMax normalize
linear
- gradient. dx, dy, phase atan dy/dy atan dx/dy
- detecteur de contours
## maven use with pom.xml dependency
```
<dependency>
    <groupId>one.empty3</groupId>
    <artifactId>feature</artifactId>
    <version>2020.4.2-SNAPSHOT</version>
</dependency>
```
## En développement. Ambitions.

https://github.com/manuelddahmen/feature 

## La conception de features 2d 3d.

Les collections d'images contenant des
_feature_ (caractéristiques) ou d'un ensemble
de _features_.

Features matching

Base de données à mettre au point.
 sum(Circle.i) dist/ c.r2

# 
file : ./settings.properties

username=user98
password=ghhyvj
host=ftp.example.net
port=21
directory=/myimages/selection/input
classname=one.empty3.feature.Transform1,one.empty3.feature.Histogram2,one.empty3.feature.GradProcess
