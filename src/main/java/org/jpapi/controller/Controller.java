/*
 * Copyright 2015 jlgranda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jpapi.controller;

import java.io.Serializable;

import org.jpapi.util.Interpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author jlgranda
 */
public abstract class Controller implements Serializable {

    private static Logger log = LoggerFactory.getLogger(Controller.class);

    protected Logger getLog() {
        return log;
    }

    protected String interpolate(String string, Object... params) {
        return Interpolator.interpolate(string, params);
    }

    protected void debug(Object object, Object... params) {
        log.debug(object.toString(), params);
    }

    protected void debug(Object object, Throwable t, Object... params) {
        log.debug(object.toString(), t, params);
    }

    protected void error(Object object, Object... params) {
        log.error(object.toString(), params);
    }

    protected void error(Object object, Throwable t, Object... params) {
        log.error(object.toString(), t, params);
    }

    protected void info(Object object, Object... params) {
        log.info(object.toString(), params);
    }

    protected void info(Object object, Throwable t, Object... params) {
        log.info(object.toString(), t, params);
    }

    protected void trace(Object object, Object... params) {
        log.trace(object.toString(), params);
    }

    protected void trace(Object object, Throwable t, Object... params) {
        log.trace(object.toString(), t, params);
    }

    protected void warn(Object object, Object... params) {
        log.warn(object.toString(), params);
    }

    protected void warn(Object object, Throwable t, Object... params) {
        log.warn(object.toString(), t, params);
    }

    protected Object getComponentInstance(String name) {
        //return Component.getInstance(name);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected Object getComponentInstance(Class clazz) {
        //return Component.getInstance(clazz);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static final long serialVersionUID = 3100628880857984156L;
}
