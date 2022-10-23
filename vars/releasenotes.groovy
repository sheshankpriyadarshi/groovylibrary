import java.io.*;
import groovy.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import hudson.model.*;

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

    date = new Date()
    sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
    writer.writeLine("Date and Time is: " + sdf.format(date))

    writer.writeLine("Build number is: ${BUILD_NUMBER}")

    changeLogsets = currentBuild.changeSets

     if(config.changes != "false")
    {

        for(change in changeLogsets)
        {
            entries = change.items

            for(entry in entries)
            {
                writer.writeLine("${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}")
                for(file in entry.affectedFiles)
                {
                    writer.writeLine("${file.editType.name} ${file.path}")
                }
            }
        }

        echo "Changes"
    }

    }

}
