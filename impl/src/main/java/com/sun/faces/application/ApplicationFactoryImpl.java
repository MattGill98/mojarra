/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.application;

import static com.sun.faces.util.Util.generateCreatedBy;
import static com.sun.faces.util.Util.notNull;
import static java.text.MessageFormat.format;
import static java.util.logging.Level.FINE;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

import com.sun.faces.util.FacesLogger;

/**
 * Default implementation of {@link ApplicationFactory}.
 */
public class ApplicationFactoryImpl extends ApplicationFactory {

    private static final Logger LOGGER = FacesLogger.APPLICATION.getLogger();

    
    // Attribute Instance Variables
    
    private final Map<String, Application> applicationHolder = new ConcurrentHashMap<>(1);
    
    private final String createdBy;

    
    // Constructors and Initializers

    public ApplicationFactoryImpl() {
        super(null);
        createdBy = generateCreatedBy(FacesContext.getCurrentInstance());
        LOGGER.log(FINE, "Created ApplicationFactory ");
    }

    /**
     * <p>Create (if needed) and return an {@link Application} instance
     * for this web application.</p>
     */
    @Override
    public Application getApplication() {
        
        ApplicationFactoryImpl applicationFactoryImpl = this;
        
        if (!applicationHolder.containsKey("default")) {
            int a;
            a = 4;
            
        }
        
        return applicationHolder.computeIfAbsent("default", e -> {
            Application applicationImpl = new ApplicationImpl();
            InjectionApplicationFactory.setApplicationInstance(applicationImpl);
            if (LOGGER.isLoggable(FINE)) {
                LOGGER.fine(format("Created Application instance ''{0}''", applicationHolder));
            }
            
            return applicationImpl;
        });
    }

    /**
     * <p>Replace the {@link Application} instance that will be
     * returned for this web application.</p>
     *
     * @param application The replacement {@link Application} instance
     */
    @Override
    public void setApplication(Application application) {
        
        notNull("application", application);
        
        applicationHolder.put("default", application);
        
        if (LOGGER.isLoggable(FINE)) {
            LOGGER.fine(format("set Application Instance to ''{0}''", application.getClass().getName()));
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " created by " + createdBy;
    }
}
