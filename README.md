
# Feature

https://drive.google.com/folderview?id=10CXWU2JPVsNMbHrAssxN23KzizzNkvHg
Documentation utilisée.

## Licence
La licence pour les sources publiées est la Gnu Gpl version 3.
## Filtres (kernels) d'images
Gauss. Multiples iterations. Tests  photos.
Gradient X, Y, debugging outter dot ptoduct. 
SobelX, SobelY. localextrema, #histogram des densites???

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
## maven use with pom.xml dependency or checkout the sources
<dependency>
    <groupId>one.empty3</groupId>
    <artifactId>features</artifactId>
    <version>2020.3-SNAPSHOT</version>
</dependency>


## En développement. Ambitions.
ça vous intéresse un logiciel de détection pour vidéo (film, enregistrement ) ou temps réel (live, webcam). Je débute dans ce domaine. Personnalisé ou personnalisable...
à venir. 
https://github.com/manuelddahmen/feature 
j'ai fini mes études depuis 10 ans je continue à me former.
## La conception de features 2d 3d.

Les collections d'images contenant une
_feature_ (caractéristiques) ou d'un ensemble
de _features_.

Features' matching

Base de données à mettre au point.
Circle. Circle r i dist/ cri
