javac -d ./temp ./src/helpers/*/*.java
cd temp || exit
jar cf ../blobbybilb-helpers-v1.jar -C . .
cd ..
rm -rf temp
cp blobbybilb-helpers-v1.jar ../csa-semester-1-final-project/lib/blobbybilb-helpers-v1.jar
