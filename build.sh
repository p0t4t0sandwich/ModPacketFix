#!/bin/bash

PROJ_ID=modpacketfix
PROJ_NAME=ModPacketFix
VERSION=1.0.0
GROUP_ID=dev/neuralnexus

# --------------------------- Functions --------------------------------

function prepareFiles() {
  # Prepare PLATFORM files
  cp ../$PROJ_NAME-$VERSION-$1.jar ./
  mv ./$PROJ_NAME-$VERSION-$1.jar ./$PROJ_NAME-$VERSION-$1.zip
  unzip ./$PROJ_NAME-$VERSION-$1.zip -d ./$1
  rm -rf ./$PROJ_NAME-$VERSION-$1.zip
}

function build() {
  mkdir -p ./$1

  # Copy common files
  cp -r ./$PROJ_NAME-all/* ./$1/

  # Zip Jar contents
  cd ./$1
  zip -r ../$1.zip ./*
  cd ../

  # Rename Jar
  mv ./$1.zip ./$1.jar

  # Generate MD5
  md5sum ./$1.jar | cut -d ' ' -f 1 > ./$1.jar.MD5

  # Move Jar
  mv ./$1.jar ../$1.jar
  mv ./$1.jar.MD5 ../$1.jar.MD5
}

# --------------------------- Setup --------------------------------

# Make directories
mkdir -p ./target/temp_build
cd ./target/temp_build

mkdir -p ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID

# --------------------------- Prepare Common --------------------------------

# Prepare bukkit files
prepareFiles bukkit

# Copy bukkit files
mv ./bukkit/$GROUP_ID/$PROJ_ID/bukkit ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./bukkit/plugin.yml ./$PROJ_NAME-all
rm -rf ./bukkit

# Prepare velocity files
prepareFiles velocity

# Copy velocity files
mv ./velocity/$GROUP_ID/$PROJ_ID/velocity ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./velocity/velocity.yml ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./velocity/velocity-plugin.json ./$PROJ_NAME-all
rm -rf ./velocity

# Prepare common files
prepareFiles common

# Copy common files
mv ./common/$GROUP_ID/$PROJ_ID/common ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
mv ./common/$GROUP_ID/$PROJ_ID/lib ./$PROJ_NAME-all/$GROUP_ID/$PROJ_ID
cp ./common/config.yml ./$PROJ_NAME-all
cp ./common/LICENSE ./$PROJ_NAME-all
cp ../../LICENSE-API ./$PROJ_NAME-all
cp ../../README.md ./$PROJ_NAME-all
rm -rf ./common

# --------------------------- Build 1.20 --------------------------------
MC_VERSION=1.20
OUT_FILE=$PROJ_NAME-$VERSION-$MC_VERSION

build $OUT_FILE

# --------------------------- Cleanup --------------------------------
cd ../
rm -rf temp_build
