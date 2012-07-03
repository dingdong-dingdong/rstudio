/*
 * ProjectBuildToolsPreferencesPane.java
 *
 * Copyright (C) 2009-12 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.projects.ui.prefs;

// TODO: make sure build stuff can't sneak in
// TODO: make all label text, etc. dynamic for non package build-all
// TODO: add Rebuild All command for makefiles
// TODO: add Clean All command for makefiles (with toolbar button)
// TODO: add drop down menu to build (rebuild, clean, config?)
// TODO: consider renaming "Project Setup" to Configure Build Tools


import org.rstudio.core.client.widget.SelectWidget;
import org.rstudio.studio.client.common.GlobalDisplay;
import org.rstudio.studio.client.projects.model.RProjectConfig;
import org.rstudio.studio.client.projects.model.RProjectOptions;
import org.rstudio.studio.client.workbench.model.Session;

import com.google.gwt.resources.client.ImageResource;

import com.google.inject.Inject;

public class ProjectBuildToolsPreferencesPane extends ProjectPreferencesPane
{
   @Inject
   public ProjectBuildToolsPreferencesPane(final Session session,
                                      GlobalDisplay globalDisplay)
   {
      session_ = session;
      globalDisplay_ = globalDisplay;
     
      buildTypeSelect_ = new BuildTypeSelectWidget();
      spaced(buildTypeSelect_);
      add(buildTypeSelect_);
   }

   @Override
   public ImageResource getIcon()
   {
      return RES.iconBuild();
   }

   @Override
   public String getName()
   {
      return "Build Tools";
   }

   @Override
   protected void initialize(RProjectOptions options)
   {
      initialConfig_ = options.getConfig();
      
      buildTypeSelect_.setValue(initialConfig_.getBuildType());
   }

   @Override
   public boolean onApply(RProjectOptions options)
   {
      RProjectConfig config = options.getConfig();
      config.setBuildType(buildTypeSelect_.getValue());
      
      // require reload if the build type changed
      return !initialConfig_.getBuildType().equals(buildTypeSelect_.getValue());
   }
   
   
   private class BuildTypeSelectWidget extends SelectWidget
   {
      public BuildTypeSelectWidget()
      {
         super("Project build type:",
               new String[]{"None", "Package", "Makefile", "Custom"});
      }
   }
   
   
  
   @SuppressWarnings("unused")
   private final Session session_;
   @SuppressWarnings("unused")
   private final GlobalDisplay globalDisplay_;
   
   private RProjectConfig initialConfig_;
   private BuildTypeSelectWidget buildTypeSelect_;
  
   private static final ProjectPreferencesDialogResources RES = 
                                    ProjectPreferencesDialogResources.INSTANCE;
}
