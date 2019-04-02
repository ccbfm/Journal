# Journal
需要引入aspectj

allprojects {
    repositories {
       
        maven { url 'https://jitpack.io' }
        
    }
}
  
dependencies {
    
    //打印log注解
    implementation 'com.github.ccbfm.Journal:journal-annotation:1.0.0'
    //注解Aspect处理
    debugImplementation 'com.github.ccbfm.Journal:journal-bearer:1.0.0'
}
