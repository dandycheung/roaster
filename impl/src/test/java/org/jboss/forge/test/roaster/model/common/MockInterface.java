/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.test.roaster.model.common;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public interface MockInterface
{
   MockSuperType lookup(String name, boolean create);

   MockSuperType lookup(int code, boolean create);

   MockSuperType lookup(int code, int anotherCode, boolean create);
}
