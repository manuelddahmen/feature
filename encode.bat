set PATH_TO_FFMPEG=c:\users\manue\ffmpeg\bin
cd ffmpeg\data
cd manu
..\..\bin\ffmpeg.exe -f image2 -s 1280x720 -i manu-%%03d.jpg ..\..\video-manu.mpg
cd ..
cd manu-dotted
..\..\bin\ffmpeg.exe -f image2 -s 1280x720 -i manu-%%03d.jpg-dotted.jpg ..\..\video-manu-dotted.mpg
cd ..
cd manu2
..\..\bin\ffmpeg.exe -f image2 -s 1280x720 -i foo2-%%03d.jpg ..\..\video-manu2.mpg
cd ..
