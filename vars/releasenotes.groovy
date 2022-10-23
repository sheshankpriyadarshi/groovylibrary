import java.io.*;
import groovy.io.*;
import java.util.Calender;
import java.text.SimpleDateFormat;

@NonCPS
def call(Map config = [:])
{
    def dir = new File(pwd());
    
    new File(dir.path + '/releasenotes.txt').withWriter('utf-8')
    {
    writer -> 
        dir.eachFileRecurse(FileType.ANY) { file -> 
        
        if(file.isDirectory()){
           writer.writeLine(file.name)
        }
        else
        {
            writer.writeLine('\t' + file.name + '\t' + file.length());
        }
        }
    
    }

    date = new Date()
    sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
    echo "Date and Time is: " + sdf.format(date)

    echo "Build number is: ${BUILD_NUMBER}"

    if(config.changes != "false")
    {
        echo "Changes"
    }

}
