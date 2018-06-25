/*********************************************************************
 * Copyright (c) 2013-2015 Carnegie Mellon University. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following acknowledgments and disclaimers.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. The names "Carnegie Mellon University," "SEI" and/or
 * "Software Engineering Institute" shall not be used to endorse or promote
 * products derived from this software without prior written permission. For
 * written permission, please contact permission@sei.cmu.edu.
 *
 * 4. Products derived from this software may not be called "SEI" nor may "SEI"
 * appear in their names without prior written permission of
 * permission@sei.cmu.edu.
 *
 * 5. Redistributions of any form whatsoever must retain the following
 * acknowledgment:
 *
 * This material is based upon work funded and supported by the Department of
 * Defense under Contract No. FA8721-05-C-0003 with Carnegie Mellon University
 * for the operation of the Software Engineering Institute, a federally funded
 * research and development center. Any opinions, findings and conclusions or
 * recommendations expressed in this material are those of the author(s) and
 * do not necessarily reflect the views of the United States Department of
 * Defense.
 *
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING
 * INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON
 * UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED,
 * AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR
 * PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE
 * MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND
 * WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 *
 * This material has been approved for public release and unlimited
 * distribution.
 *
 * @author James Edmondson <jedmondson@gmail.com>
 *********************************************************************/

package ai.shield.shieldaidemo.tests;


import ai.madara.knowledge.KnowledgeBase;
import ai.madara.knowledge.KnowledgeMap;
import ai.madara.knowledge.containers.Integer;
import ai.madara.knowledge.containers.String;

/**
 * This class is a tester for basic KnowledgeBase functionality
 */
public class TestKnowledgeBase {
    public static void main(java.lang.String... args) throws InterruptedException, Exception {
        KnowledgeBase knowledge = new KnowledgeBase();

        Integer age = new Integer();
        age.setName(knowledge, "age");
        age.set(24);

        String name = new String();
        name.setName(knowledge, "name");
        name.set("Alfred Mooney");

        String occupation = new String();
        occupation.setName(knowledge, "occupation");
        occupation.set("Moonlighter");

        java.lang.String contents = knowledge.toString();

        System.out.println(contents);

        knowledge.free();

        knowledge.set("test", 100);

        knowledge.set("device.0.test.settings", "This is a test (0)");
        knowledge.set("device.1.test.settings", "This is a test (1)");
        knowledge.set("device.2.test.settings", "This is a test (2)");
        knowledge.set("device.2", "is a real device");
        knowledge.set("device.location", "USA");
        System.out.println("Amit: " + knowledge.get("device.0.test.settings") + " --  " + knowledge.toString());


        KnowledgeMap deviceSettings = knowledge.toKnowledgeMap("device.", "settings");

        System.out.print("Testing toKnowledgeMap with suffix and prefix: ");

        if (deviceSettings.containsKey("device.0.test.settings") &&
                deviceSettings.containsKey("device.1.test.settings") &&
                deviceSettings.containsKey("device.2.test.settings") &&
                !deviceSettings.containsKey("device.2") &&
                !deviceSettings.containsKey("device.location")) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAIL");
        }


        // print all knowledge
        knowledge.print();

        knowledge.print("And we're done.\n");
    }
}
