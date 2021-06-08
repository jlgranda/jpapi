/*
 * Copyright (C) 2015 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jpapi.model;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
public class JPAPIEntityTuplizer extends PojoEntityTuplizer {
    
    private static Logger log = LoggerFactory.getLogger(JPAPIEntityTuplizer.class);

    public JPAPIEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
        super(entityMetamodel, mappedEntity);
        log.info("Finaliza constructor de JPAPIEntityTuplizer");
    }

}
