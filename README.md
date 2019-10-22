# RemolveButterknifeKnifePlus
## 介绍
RemolveButterknifeKnifePlus是个基于RemolveButterknifeKnife`https://github.com/u3shadow/RemoveButterKnife`开发的android studio插件。由于RemolveButterknifeKnife
插件去除butterknife注解生成的代码冗余，为了让去除后的代码更加简洁，改进该插件。
## 改进点
1. 原始生成的findViewById代码为(TextView)findViewById(R.id.tv01)，可以看出这个插件是好久之前写的了。
在新版的androidStudio中，只要生成findViewById(R.id.tv01)即可。