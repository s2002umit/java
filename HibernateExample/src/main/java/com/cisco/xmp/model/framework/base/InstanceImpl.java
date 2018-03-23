///******************************************************************************
// * Copyright (c) 2009 by Cisco Systems, Inc. All rights reserved.
// * 
// * This software contains proprietary information which shall not be reproduced
// * or transferred to other documents and shall not be disclosed to others or
// * used for manufacturing or any other purpose without prior permission of Cisco
// * Systems.
// * 
// *****************************************************************************/
//
//package com.cisco.xmp.model.framework.base;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.hibernate.collection.PersistentBag;
//import org.hibernate.collection.PersistentList;
//import org.hibernate.collection.PersistentSet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cisco.xmp.model.framework.EnumMarker;
//import com.cisco.xmp.model.framework.IgnoreLazyAspectDuringQuiteOperation;
//import com.cisco.xmp.model.framework.ModelFrameworkException;
//import com.cisco.xmp.model.framework.Resource;
//import com.cisco.xmp.model.framework.Utilities;
//import com.cisco.xmp.model.framework.identity.IdRef;
//import com.cisco.xmp.model.framework.metadata.AttributeMetadata;
//import com.cisco.xmp.model.framework.metadata.EntityMetadata;
//import com.cisco.xmp.model.framework.metadata.PropertyMetadata;
//import com.cisco.xmp.model.framework.primitive.DeployPendingEnum;
//import com.cisco.xmp.model.framework.visitor.IVisitable;
//import com.cisco.xmp.model.framework.visitor.IVisitor;
//import com.cisco.xmp.model.framework.visitor.VisitorException;
//
///**
// * Base class for all entities and association classes.
// * 
// * @author kchamber
// * 
// */
//public abstract class InstanceImpl implements Resource, IVisitable, Serializable, Comparable<InstanceImpl>, Cloneable {
//
//    private static Logger logger = LoggerFactory.getLogger(Utilities.class);
//
//    /**
//     * 
//     */
//
//    private static final long serialVersionUID = 1L;
//
//    private String instanceUuid;
//
//    private long instanceId;
//
//    private Long authEntityId;
//
//    private String displayName; // displayName being promoted to top level attribute
//
//    private Integer authEntityClass;
//
//    private int _orderedListOEIndex;
//    private String _orderedListOEAssocName;
//    private int _creationOrderIndex;
//    private boolean _isBeingChanged;
//    
//    private DeployPendingEnum deployPending = DeployPendingEnum.NONE; 
//
//    public static ThreadLocal<Boolean> isQuieteOperation = new ThreadLocal<Boolean>() {
//        @Override
//        protected synchronized Boolean initialValue() {
//            return Boolean.FALSE;
//        }
//    };
//
//    /**
//     * Used for hibernate optimistic locking. Not for application use.
//     */
//    private int instanceVersion;
//
//    /**
//     * Base comparator for comparing instances. Compare determined using the following order:
//     * <ol>
//     * <li>BusinessKey, (if defined)</li>
//     * <li>Database primary key</li>
//     * </ol>
//     * 
//     * @param o
//     *            object to be compared with
//     * @return value to be used for ordering
//     */
//    public int compareTo(InstanceImpl o) {
//        if (o == null || o.getEntityClass() != getEntityClass()) {
//            return -1;
//        }
//
//       
//        if (o.getInstanceId() == getInstanceId()) {
//            return 0;
//        }
//
//        if (o.getInstanceId() < getInstanceId()) {
//            return -1;
//        }
//
//        return 1;
//    }
//
//    /**
//     * Base equals for checking equality of instances Uses base reference equality. Equality determined using the
//     * following order:
//     * <ol>
//     * <li>BusinessKey, (if defined)</li>
//     * <li>Database primary key</li>
//     * <li>Java reference equality</li>
//     * </ol>
//     * 
//     * @param o
//     *            object to compare against
//     * @return boolean indicating equality
//     */
//    @Override
//    public boolean equals(Object o) {
//
//        if (o == null || (!(o instanceof InstanceImpl)) || ((InstanceImpl) o).getEntityClass() != getEntityClass()) {
//            return false;
//        }
//
//        if (hasBusinessKeys()) {
//            return equalsByBusinessKey(o);
//        }
//
//        InstanceImpl o1 = (InstanceImpl) o;
//        if (o1.getInstanceId() == 0 || getInstanceId() == 0) {
//            return super.equals(o);
//        }
//
//        return (o1.getInstanceId() == getInstanceId());
//    }
//
//    private static final long ALL_ONES_BIT_MASK = 0xffffffffL;
//    private static final int BIT_SHIFT_32 = 32;
//
//    /**
//     * Base haseCode for the instance. Hash determined using the following order:
//     * <ol>
//     * <li>BusinessKey, (if defined)</li>
//     * <li>Database primary key</li>
//     * <li>Java Object hash</li>
//     * </ol>
//     * 
//     * 
//     * @return a hashcode
//     */
//    @Override
//    public int hashCode() {
//
//
//        // otherwise use a db id if we have it.
//        return (getInstanceId() == 0) ? super.hashCode()
//                : (((int) (getInstanceId() & ALL_ONES_BIT_MASK)) | ((int) (getInstanceId() >>> BIT_SHIFT_32)));
//    }
//
//    /**
//     * Compute equality based upon database primary key.
//     * 
//     * @param o
//     *            object to compare against
//     * @return boolean indicating equality
//     */
//    public boolean equalsByDbId(Object o) {
//        if (o == null || (!(o instanceof InstanceImpl)) || ((InstanceImpl) o).getEntityClass() != getEntityClass()) {
//            return false;
//        }
//
//        InstanceImpl o1 = (InstanceImpl) o;
//
//        return (o1.getInstanceId() == getInstanceId());
//    }
//
//    /**
//     * Compute equality based upon business keys for this instance. If this instance defines no business keys and the
//     * compared objects are of the same type, this will return true.
//     * 
//     * @param o
//     *            object to compare against
//     * @return boolean indicating equality
//     */
//    public boolean equalsByBusinessKey(Object o) {
//        if (o == null || (!(o instanceof InstanceImpl)) || ((InstanceImpl) o).getEntityClass() != getEntityClass()) {
//            return false;
//        }
//
//        InstanceImpl o1 = (InstanceImpl) o;
//
//        for (AttributeMetadata pm : getBusinessKeyAttributes()) {
//            Object res1 = getAttributeValue(pm.getName());
//            Object res2 = o1.getAttributeValue(pm.getName());
//
//            if (res1 == null) {
//                if (res2 != null) {
//                    return false;
//                }
//            } else if (!res1.equals(res2)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * Setter for instance Id. This should be set by hibernate and not by application code.
//     * 
//     * @param instanceId
//     *            the primary key
//     */
//    public void setInstanceId(long instanceId) {
//        this.instanceId = instanceId;
//    }
//
//    /**
//     * Getter for the primary key.
//     * 
//     * @return the primary key for this instance
//     */
//    public long getInstanceId() {
//        return instanceId;
//    }
//
//    /**
//     * Setter for the uuid
//     * 
//     */
//    public void setInstanceUuid(String instanceUuid) {
//        this.instanceUuid = instanceUuid;
//    }
//
//    /**
//     * Getter for the instanceUuid.
//     * 
//     * @return the instanceUuid for this instance
//     */
//    public String getInstanceUuid() {
//
//        return instanceUuid;
//    }
//
//    /**
//     * Setter for version attribute. Used by hibernate optimistic locking. Internal Use Only.
//     * 
//     * @param instanceVersion
//     *            the version identifier
//     */
//    public void setInstanceVersion(int instanceVersion) {
//        this.instanceVersion = instanceVersion;
//    }
//
//    /**
//     * Getter for the version attribute.
//     * 
//     * @return the version attribute.
//     */
//    public int getInstanceVersion() {
//        return instanceVersion;
//    }
//
//    /**
//     * Return the Id of the authorizing entity for this instance.
//     * 
//     * @return the id of the authorizing entity.
//     */
//    public Long getAuthEntityId() {
//        return authEntityId;
//    }
//
//    /**
//     * Setter for the authorizing entity id.
//     * 
//     * @param authEntityId
//     *            id of the authorizing entity for this instance
//     */
//    public void setAuthEntityId(Long authEntityId) {
//        this.authEntityId = authEntityId;
//    }
//
//    /**
//     * Class ID for the authorizing entity for this instance.
//     * 
//     * @return the classId of the authorizing entity class.
//     */
//    public Integer getAuthEntityClass() {
//        return authEntityClass;
//    }
//
//    /**
//     * Setter for the authorizing entity class ID.
//     * 
//     * @param authEntityClass
//     *            authoritative entity class for this instance
//     */
//    public void setAuthEntityClass(Integer authEntityClass) {
//        this.authEntityClass = authEntityClass;
//    }
//
//    /**
//     * Generate a key reference for this object.
//     * 
//     * @return an IdRef representing this instance.
//     */
//    public IdRef getInternalKey() {
//        return new IdRef(getEntityClass(), getInstanceId());
//    }
//
//    /**
//     * Return a Map of key references to objects.
//     * 
//     * @param objs
//     *            to have their key map returned
//     * @return a map of idRef key to object
//     */
//    public static Map<IdRef, InstanceImpl> getInternalKeyMap(Collection<? extends InstanceImpl> objs) {
//        final Map<IdRef, InstanceImpl> result = new HashMap<IdRef, InstanceImpl>();
//        for (InstanceImpl obj : objs) {
//            result.put(obj.getInternalKey(), obj);
//        }
//        return result;
//    }
//
//    /**
//     * A handle that allows hidden setting of an identified singular association.
//     * 
//     * @author jworrell
//     * 
//     */
//    protected abstract static class AssociationHandle {
//        protected abstract void setQuietly(InstanceImpl target, Object value);
//    }
//
//    /**
//     * A handle that allows hidden setting of an identified multiple association.
//     * 
//     * @author jworrell
//     * 
//     */
//    protected abstract static class MultiAssociationHandle extends AssociationHandle {
//        @Override
//        protected abstract void setQuietly(InstanceImpl target, Object value);
//
//        protected abstract void addQuietly(InstanceImpl target, Object value);
//
//        protected abstract void removeQuietly(InstanceImpl target, Object value);
//    }
//
//    protected static void setQuietly(AssociationHandle handle, InstanceImpl base, Object value) {
//        if (base != null) {
//            isQuieteOperation.set(Boolean.TRUE);
//            try {
//                handle.setQuietly(base, value);
//            } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                // Ignore
//            } finally {
//                isQuieteOperation.set(Boolean.FALSE);
//            }
//        }
//    }
//
//    protected static void addQuietly(AssociationHandle handle, InstanceImpl base, Object value) {
//        if (base != null) {
//            isQuieteOperation.set(Boolean.TRUE);
//            try {
//                handle.setQuietly(base, value);
//            } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                // Ignore
//            } finally {
//                isQuieteOperation.set(Boolean.FALSE);
//            }
//        }
//    }
//
//    protected static void addQuietly(MultiAssociationHandle handle, InstanceImpl base, Object value) {
//        if (base != null) {
//            isQuieteOperation.set(Boolean.TRUE);
//            try {
//                handle.addQuietly(base, value);
//            } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                // Ignore
//            } finally {
//                isQuieteOperation.set(Boolean.FALSE);
//            }
//        }
//    }
//
//    protected static void removeQuietly(AssociationHandle handle, InstanceImpl base, Object value) {
//        if (base != null) {
//            isQuieteOperation.set(Boolean.TRUE);
//            try {
//                handle.setQuietly(base, null);
//            } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                // Ignore
//            } finally {
//                isQuieteOperation.set(Boolean.FALSE);
//            }
//        }
//    }
//
//    protected static void removeQuietly(MultiAssociationHandle handle, InstanceImpl base, Object value) {
//        if (base != null) {
//            isQuieteOperation.set(Boolean.TRUE);
//            try {
//                handle.removeQuietly(base, value);
//            } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                // Ignore
//            } finally {
//                isQuieteOperation.set(Boolean.FALSE);
//            }
//        }
//    }
//
//    protected static void setQuietly(AssociationHandle handle, Collection<? extends InstanceImpl> bases, Object value) {
//        isQuieteOperation.set(Boolean.TRUE);
//        try {
//            for (InstanceImpl base : bases) {
//                try {
//                    handle.setQuietly(base, value);
//                } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                    // Ignore
//                }
//            }
//        } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//
//        } finally {
//            isQuieteOperation.set(Boolean.FALSE);
//        }
//    }
//
//    protected static void addQuietly(AssociationHandle handle, Collection<? extends InstanceImpl> bases, Object value) {
//        isQuieteOperation.set(Boolean.TRUE);
//        try {
//            for (InstanceImpl base : bases) {
//                try {
//                    if (handle instanceof MultiAssociationHandle) {
//                        addQuietly((MultiAssociationHandle) handle, base, value);
//                    } else {
//                        addQuietly(handle, base, value);
//                    }
//                } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                    // Ignore
//                }
//            }
//
//        } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//
//        } finally {
//            isQuieteOperation.set(Boolean.FALSE);
//        }
//    }
//
//    protected static void removeQuietly(AssociationHandle handle, Collection<? extends InstanceImpl> bases, Object value) {
//        isQuieteOperation.set(Boolean.TRUE);
//        try {
//            for (InstanceImpl base : bases) {
//                try {
//                    if (handle instanceof MultiAssociationHandle) {
//                        removeQuietly((MultiAssociationHandle) handle, base, value);
//                    } else {
//                        removeQuietly(handle, base, value);
//                    }
//                } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//                    // Ignore
//                }
//            }
//        } catch (IgnoreLazyAspectDuringQuiteOperation e) {
//
//        } finally {
//            isQuieteOperation.set(Boolean.FALSE);
//        }
//    }
//
//    /**
//     * Wrap up remove operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> from which to remove
//     * @param v
//     *            the <code>Object</code> to remove
//     */
//    @SuppressWarnings("unchecked")
//    protected static void remove(Collection c, Object v) {
//        c.remove(v);
//    }
//
//    /**
//     * For collections that are lists, support removing by position.
//     * 
//     * @param index
//     *            the position at which to remove
//     * @param c
//     *            the <code>List</code> object
//     * @return returns the object that was removed.
//     */
//    @SuppressWarnings("unchecked")
//    protected static Object remove(int index, List c) {
//        return c.remove(index);
//    }
//
//    /**
//     * Wrap up removeAll operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> from which to remove
//     * @param v
//     *            the <code>Collection</code> to remove
//     */
//    @SuppressWarnings("unchecked")
//    protected static void removeAll(Collection c, Collection v) {
//        c.removeAll(v);
//    }
//
//    /**
//     * Wrap up add operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code>
//     * @param v
//     *            the <code>Object</code> to add
//     */
//    @SuppressWarnings("unchecked")
//    protected static void add(Collection c, Object v) {
//        c.add(v);
//    }
//
//    /**
//     * For collections that are lists, support adding by position
//     * 
//     * @param index
//     *            the position at which to add
//     * @param c
//     *            the <code>List</code>
//     * @param v
//     *            the <code>Object</code> to add
//     */
//    @SuppressWarnings("unchecked")
//    protected static void add(int index, List c, Object v) {
//        c.add(index, v);
//    }
//
//    /**
//     * Wrap up addAll operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> to which to add
//     * @param v
//     *            the <code>Collection</code> to add
//     */
//    @SuppressWarnings("unchecked")
//    protected static void addAll(Collection c, Collection v) {
//        c.addAll(v);
//    }
//
//    /**
//     * Wrap up remove operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> from which to remove
//     * @param v
//     *            the <code>Object</code> to remove
//     */
//    @SuppressWarnings("unchecked")
//    protected static void remove(Map c, Object v) {
//        c.values().remove(v);
//    }
//
//    /**
//     * Wrap up removeAll operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> from which to remove
//     * @param v
//     *            the <code>Collection</code> to remove
//     */
//    @SuppressWarnings("unchecked")
//    protected static void removeAll(Map c, Collection v) {
//        c.values().removeAll(v);
//    }
//
//    /**
//     * Wrap up add operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> to which to add
//     * @param v
//     *            the <code>Object</code> to add
//     */
//    @SuppressWarnings("unchecked")
//    protected static void add(Map c, Object v) {
//        c.put(((InstanceImpl) v).getInstanceId(), v);
//    }
//
//    /**
//     * Wrap up addAll operation to ease changes of semantics.
//     * 
//     * @param c
//     *            the <code>Collection</code> to which to add
//     * @param v
//     *            the <code>Collection</code> to add
//     */
//    @SuppressWarnings("unchecked")
//    protected static void addAll(Map c, Collection v) {
//        for (Object i : v) {
//            add(c, (i));
//        }
//    }
//
//    /**
//     * Traverses the supplied <code>Collection</code> checking each element in turn to ensure that it is of the type
//     * specified by the supplied <code>Class</code> object.
//     * 
//     * @param type
//     *            a <code>Class</code> that defines the type required for each of elements in the supplied
//     *            <code>Collection</code>
//     * @param c
//     *            the <code>Collection</code> to be checked for type compliance
//     * @return the value <code>true</code> if all elements of the supplied <code>Collection</code> are of the correct
//     *         type and <code>false</code> otherwise
//     */
//    @SuppressWarnings("unchecked")
//    protected static boolean checkTypes(Class type, Collection c) {
//        boolean ok = true;
//        if (c == null) {
//            return ok;
//        }
//
//        for (Object i : c) {
//            ok = type.isInstance(i);
//            if (!ok) {
//                return false;
//            }
//        }
//        return ok;
//    }
//
//    /**
//     * Visitor pattern for accepting a visitor.
//     * 
//     * @param visitor
//     *            IVisitor instance to visit this element
//     * @return an object from the visitor
//     * @throws VisitorException
//     *             a visitor exception
//     */
//    public Object accept(IVisitor visitor) throws VisitorException {
//        return visitor.visit(this);
//    }
//
//   
//
//   
//
//    /**
//     * Notify any registered observers regarding updates.
//     * 
//     * @param propertyName
//     *            property being updated.
//     * @deprecated
//     */
//    @Deprecated
//    public void notify(String propertyName) {
//    }
//
//    /**
//     * Provide a shallow attributes-only copy of this instance. Associations are not copied.
//     * 
//     * @return a shallow copy of this instance. Attributes are shallow copied, (reference the same object). Associations
//     *         are not copied.
//     */
//    @Override
//    public Object clone() {
//
//        // Don't call super.clone() because we don't want shallow-copies of the
//        // associations.
//        // Nulling them out, after the fact, would mess up the other ends of the
//        // association.
//        // Instead, create an instance and copy over the attributes.
//        try {
//            InstanceImpl ii = getEntityClass().newInstance();
//            Map<String, AttributeMetadata> attrMap = Utilities.onlyAttributeMetadata(ii.getPropertyMetadataMap());
//
//            for (AttributeMetadata attr : attrMap.values()) {
//                if (!attr.getName().equals("instanceId") && (!attr.getName().equals("instanceUuid"))) {
//                    Class setterClass = (attr.isCollection() ? attr.getCollectionInterfaceType() : attr.getType());
//
//                    ii.setAttributeValue(attr.getName(), setterClass, this.getAttributeValue(attr.getName()));
//                }
//            }
//
//            return ii;
//
//        } catch (Exception e) {
//            throw new ModelFrameworkException("Exception cloning object", e);
//        }
//    }
//
//    /**
//     * Provide a deep copy attributes-only of this instance. Associations are not copied.
//     * 
//     * @return a deep copy of this instance. Attributes are deep copied. Associations are not copied.
//     */
//    public Object deepCopy() {
//
//        // create a shallow copy of ourselves
//        InstanceImpl ii = (InstanceImpl) this.clone();
//
//        Map<String, AttributeMetadata> attrMap = Utilities.onlyAttributeMetadata(ii.getPropertyMetadataMap());
//
//        // update the attributes to reference deep copies.
//        for (AttributeMetadata attr : attrMap.values()) {
//            if (!attr.getName().equals("instanceId") && (!attr.getName().equals("instanceUuid"))) {
//
//                Object o = this.getAttributeValue(attr.getName());
//
//                logger.debug("in deepCopy: " + attr.getName() + ", " + o);
//                Class setterClass = (attr.isCollection() ? attr.getCollectionInterfaceType() : attr.getType());
//
//                if (o != null) {
//                    Object deepAttr = null;
//                    try {
//                        deepAttr = getDeepCloning(o);
//                    } catch (Exception e) {
//                        throw new ModelFrameworkException("Exception cloning object: "
//                                + this.getEntityClass().getName() + " attribute: " + attr.getName(), e);
//                    }
//                    ii.setAttributeValue(attr.getName(), setterClass, deepAttr);
//                } else {
//                    ii.setAttributeValue(attr.getName(), setterClass, o);
//                }
//            }
//        }
//
//        return ii;
//
//    }
//
//    /**
//     * Return a deep clone of the object. This is accomplished by serializing the object into a byte array, and then
//     * reconstituting a new object.
//     * 
//     * @param obj
//     *            to be cloned
//     * @return returns a clone
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    private Object getDeepCloning(Object obj) throws IOException, ClassNotFoundException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(obj);
//        oos.flush();
//        oos.close();
//
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//        ObjectInputStream ois = new ObjectInputStream(bais);
//        return ois.readObject();
//    }
//
//    /**
//     * Work around for hidden constructor...
//     * 
//     * @param <T>
//     *            POJOBase subclass
//     * @param pojoClass
//     *            The class to instantiate
//     * @return the instantiated class
//     */
//    protected <T extends InstanceImpl> T createPojo(Class<T> pojoClass) {
//        T p;
//        try {
//            //
//            Constructor<T> c = pojoClass.getDeclaredConstructor();
//            c.setAccessible(true);
//            p = c.newInstance();
//        } catch (Exception e) {
//            throw new ModelFrameworkException("Cannot create Model object.", e);
//        }
//        return p;
//    }
//
//    /**
//     * Generic getter for any property.
//     * 
//     * @param attrName
//     *            attribute name to be retrieved
//     * @return the return value from invoking the getter
//     * @throws ModelFrameworkException
//     *             on an exception invoking the methods
//     */
//    public Object getAttributeValue(String attrName) throws ModelFrameworkException {
//
//        PropertyMetadata md = this.getPropertyMetadataMap().get(attrName);
//        if (md == null) {
//            throw new ModelFrameworkException("Attribute named " + attrName + " on class: " + this.getEntityClass()
//                    + " does not exist.");
//        }
//
//        return md.getValue(this);
//    }
//
//    /**
//     * Generic getter for any property.
//     * 
//     * @param attrName
//     *            attribute name to be retrieved
//     * @param overrideAccesscheck
//     *            boolean to override the access check on the getter. Used internally.
//     * @return the return value from invoking the getter
//     * @throws ModelFrameworkException
//     *             on an exception invoking the methods
//     */
//    public Object getAttributeValue(String attrName, boolean overrideAccessCheck) throws ModelFrameworkException {
//        Method getter = AccessorUtil.getInstance()
//                .getGetterMethod(this.getEntityClass(), attrName, overrideAccessCheck);
//        if (getter != null) {
//            try {
//                return getter.invoke(this);
//            } catch (Exception e) {
//                throw new ModelFrameworkException("Exception invoking getter method for " + attrName + " on class: "
//                        + this.getEntityClass(), e);
//            }
//        } else {
//            throw new ModelFrameworkException("No such getter for attribute " + attrName + " on class: "
//                    + this.getEntityClass());
//        }
//    }
//
//    /**
//     * Generic setter for any property.
//     * 
//     * @param attrName
//     *            attribute name to be set
//     * @param attrValue
//     *            attribute value to be set
//     * @throws ModelFrameworkException
//     *             on an exception invoking the methods
//     */
//    public void setAttributeValue(String attrName, Object attrValue) throws ModelFrameworkException {
//        PropertyMetadata md = this.getPropertyMetadataMap().get(attrName);
//        if (md == null) {
//            throw new ModelFrameworkException("Attribute named " + attrName + " on class: " + this.getEntityClass()
//                    + " does not exist.");
//        }
//
//        md.setValue(this, attrValue);
//    }
//
//    /**
//     * Generic setter for any property.
//     * 
//     * @param attrName
//     *            attribute name to be set
//     * @param argClass
//     *            class to be used in looking up the adder method
//     * @param attrValue
//     *            attribute value to be set
//     * @throws ModelFrameworkException
//     *             on an exception invoking the methods
//     */
//    public void setAttributeValue(String attrName, Class<?> argClass, Object attrValue) throws ModelFrameworkException {
//
//        // call other method, don't care about argClass
//        setAttributeValue(attrName, attrValue);
//    }
//
//    /**
//     * Generic adder for any property. This method can only be used with Collection datatypes.
//     * 
//     * @param attrName
//     *            attribute name to be added
//     * @param argClass
//     *            class to be used in looking up the adder method
//     * @param attrValue
//     *            attribute value to be added
//     * @throws ModelFrameworkException
//     *             on an exception invoking the methods
//     */
//    public void addAttributeValue(String attrName, Class<?> argClass, Object attrValue) throws ModelFrameworkException {
//        Method adder = AccessorUtil.getInstance().getAdderMethod(this.getEntityClass(), attrName);
//        if (adder != null) {
//            try {
//                adder.invoke(this, new Object[] { attrValue });
//            } catch (Exception e) {
//                throw new ModelFrameworkException("Exception invoking adder method for " + attrName + " on class: "
//                        + this.getEntityClass(), e);
//            }
//        } else {
//            throw new ModelFrameworkException("No such adder for attribute " + attrName + " on class: "
//                    + this.getEntityClass());
//        }
//    }
//
//    /**
//     * Given an attribute name, return the attribute type.
//     * 
//     * @param attrName
//     *            name of the attribute
//     * @return type of the attribute
//     * @throws ModelFrameworkException
//     *             if a getter for the attribute cannot be found
//     */
//    public Class<?> getAttributeType(String attrName) throws ModelFrameworkException {
//        Method getter = AccessorUtil.getInstance().getGetterMethod(this.getEntityClass(), attrName);
//        if (getter != null) {
//            return getter.getReturnType();
//        } else {
//            throw new ModelFrameworkException("No such setter for attribute " + attrName + " on class: "
//                    + this.getEntityClass());
//        }
//    }
//
//    /**
//     * Return the list of attributes that are used as the business attributes for this object.
//     * 
//     * @return a List of PropertyMetatdata identifying natural-id attributes.
//     */
//    public List<AttributeMetadata> getBusinessKeyAttributes() {
//
//        // by default return an empty collection
//        return new ArrayList<AttributeMetadata>();
//    }
//
//    /**
//     * Return the list of attributes that are used as the display name for this object.
//     * 
//     * @return a List of PropertyMetata identifying display name attributes
//     */
//    public List<AttributeMetadata> getDisplayNameAttributes() {
//
//        // by default return an empty collection
//        return new ArrayList<AttributeMetadata>();
//    }
//
//    /**
//     * Return the map of alternate key sequences to be used as alternate keys for this object.
//     * 
//     * @return a map of alternate key names to a list of alternate key attributes to be used for this object.
//     */
//    public Map<String, List<AttributeMetadata>> getAlternateKeyAttributes() {
//        return null;
//    }
//
//    /**
//     * Return the metadata for this entity.
//     * 
//     * @return metatdata for this entity
//     */
//    public abstract EntityMetadata getEntityMetadata();
//
//    /**
//     * Return the property metadata for this entity.
//     * 
//     * @return a Map describing the attribute metadata for this entity
//     */
//    public abstract Map<String, PropertyMetadata> getPropertyMetadataMap();
//
//    /**
//     * Return the unique component name for this element, (e.g., Card, Port, etc).
//     * 
//     * @return unique component name
//     */
//    public abstract String getComponentName();
//
//    
//
//    /**
//     * This method is needed to prevent Java from optimizing away the target invocation for getClass() when trying to
//     * get the class for Hibernate proxy objects. In this case, we want the class of the target, not of the proxy
//     * itself.
//     * 
//     * @return the describing class for this entity.
//     */
//    public Class<? extends InstanceImpl> getEntityClass() {
//        return InstanceImpl.class;
//    }
//
//    /**
//     * Gets the entity class name.
//     * 
//     * @return the entity class name
//     */
//    public String getEntityClassName() {
//        return getEntityClass().getName();
//    }
//
//    /**
//     * Gets the entity class simple name.
//     * 
//     * @return the entity class simple name
//     */
//    public String getEntityClassSimpleName() {
//        return getEntityClass().getSimpleName();
//    }
//
//   
//  
//
//    /**
//     * Return a string representation of the business key. Returns null for instances that define no business keys.
//     * Throws IllegalArgumentException when a BusinessKey attributes is null if allowNull is false.
//     * 
//     * @param allowNulls
//     *            -- specifies to ignore nulls as BusinessKey values.
//     * 
//     * @return a business key for this element.
//     */
//   
//
//    /**
//     * Returns a map of Business Key AttributeMetadata to values for this instance.
//     * 
//     * @return the map
//     */
//    public Map<AttributeMetadata, Object> getBusinessKeyAttributeMap() {
//        return getPropertyValueMap(getBusinessKeyAttributes());
//    }
//
//    /**
//     * Return a boolean indicating whether this element has business keys.
//     * 
//     * @return a boolean
//     */
//    public boolean hasBusinessKeys() {
//        List<AttributeMetadata> keys = getBusinessKeyAttributes();
//        return ((keys != null) && (keys.size() != 0));
//    }
//
//    /**
//     * Convenience routine to return a map of attribute names/values for this instance.
//     * 
//     * @param attributeList
//     *            list of attribute names
//     * @return a map
//     */
//    public Map<String, Object> getNameValueMap(List<String> attributeList) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        for (String attribute : attributeList) {
//            map.put(attribute, this.getAttributeValue(attribute));
//        }
//
//        return map;
//    }
//
//    /**
//     * 
//     * Copy attributes from object into this object.
//     * 
//     * @param fromObj
//     *            object to copy attributes from
//     * @param attrList
//     *            list of attributes to be copied.
//     */
//    public void copyAttributes(InstanceImpl fromObj, Collection<AttributeMetadata> attrList) {
//        for (AttributeMetadata attr : attrList) {
//
//            Object value = unWrapHibernateCollection(fromObj.getAttributeValue(attr.getName()));
//            this.setAttributeValue(attr.getName(), value);
//        }
//    }
//
//    private Object unWrapHibernateCollection(Object c) {
//        Object toRet = c;
//
//        if (c instanceof PersistentList || c instanceof PersistentBag) {
//            toRet = new ArrayList((Collection) c);
//        } else if (c instanceof PersistentSet) {
//            toRet = new HashSet((Collection) c);
//        }
//
//        return toRet;
//    }
//
//    private static final int HASH_17 = 17;
//
//    
//    /**
//     * XMP inventory computes a hashcode, (fingerprint), for a given entity of its attributes to ascertain changes in
//     * the network. Computing that hashCode has been problematic across server restarts because Java enum hashCode()
//     * values are not consistent across a JVM restart. Therefore, this method will be used for computing the hashCode
//     * for a Set where if elements of the set are an XMP generated enum, then the hashCode() of its string
//     * representation will be used. Otherwise, the normal hashCode() method for the element is used.
//     * 
//     * This code was borrowed/enhanced from the Java 6 implementation of AbstractSet.
//     * 
//     * @param set
//     * @return
//     */
//    public static int enumAwareHashCodeForSet(Set set) {
//        int h = 0;
//        Iterator i = set.iterator();
//        while (i.hasNext()) {
//            Object obj = i.next();
//            if (obj != null)
//                if (obj instanceof EnumMarker) {
//                    h += obj.toString().hashCode();
//                } else {
//                    h += obj.hashCode();
//                }
//        }
//        return h;
//    }
//
//    /**
//     * XMP inventory computes a hashcode, (fingerprint), for a given entity of its attributes to ascertain changes in
//     * the network. Computing that hashCode has been problematic across server restarts because Java enum hashCode()
//     * values are not consistent across a JVM restart. Therefore, this method will be used for computing the hashCode
//     * for a List where if elements of the list are an XMP generated enum, then the hashCode() of its string
//     * representation will be used. Otherwise, the normal hashCode() method for the element is used.
//     * 
//     * This code was borrowed/enhanced from the Java 6 implementation of AbstractList.
//     * 
//     * @param list
//     * @return
//     */
//    public static int enumAwareHashCodeForList(List list) {
//        int hashCode = 1;
//        Iterator i = list.iterator();
//        while (i.hasNext()) {
//            Object obj = i.next();
//            int objHc = 0;
//            if (obj != null) {
//                if (obj instanceof EnumMarker) {
//                    objHc = obj.toString().hashCode();
//                } else {
//                    objHc = obj.hashCode();
//                }
//            }
//            hashCode = 31 * hashCode + objHc;
//        }
//        return hashCode;
//    }
//
//    /**
//     * Return an attributeMetadata list for this element using the specified attribute names.
//     * 
//     * @param attributeNames
//     *            specified attribute names to be returned
//     * @return a list of AttributeMetadata
//     */
//    public List<AttributeMetadata> getAttributesByName(String attributeNames[]) {
//
//        if ((attributeNames == null) || (attributeNames.length == 0)) {
//            return null;
//        }
//
//        List<AttributeMetadata> list = new ArrayList<AttributeMetadata>();
//        Map<String, PropertyMetadata> map = getPropertyMetadataMap();
//        for (String s : attributeNames) {
//            Object o = map.get(s);
//            if (o == null || !(o instanceof AttributeMetadata)) {
//                throw new ModelFrameworkException("Attribute " + s + " is not an attribute on " + this.getEntityClass());
//            }
//
//            list.add((AttributeMetadata) o);
//        }
//
//        return list;
//    }
//
//    /**
//     * Convenience routine to return a map of attribute names/values for this instance.
//     * 
//     * @param attributeList
//     *            list of attribute names
//     * @return a map
//     */
//    public Map<AttributeMetadata, Object> getPropertyValueMap(List<AttributeMetadata> attributeList) {
//
//        if (attributeList == null) {
//            return null;
//        }
//
//        Map<AttributeMetadata, Object> map = new HashMap<AttributeMetadata, Object>();
//
//        for (AttributeMetadata pm : attributeList) {
//            map.put(pm, this.getAttributeValue(pm.getName()));
//        }
//
//        return map;
//    }
//
//    /**
//     * Return a display name string that can be used for this instance. If no attributes are annotated as displayName
//     * attributes, then this returns a businessKey string. If no business keys are identified then display name returns
//     * the component type with the database instanceId.
//     * 
//     * @return a string display name for this component.
//     */
//    public String getDisplayName() {
//
//        if (this.displayName != null) {
//            return this.displayName;
//        }
//
//        
//        return "test";
//    }
//    
//    public void setDisplayName(String displayName){
//        this.displayName = displayName;
//    }
//
// 
//
//   
//    /**
//     * ICE specific
//     * 
//     * @return the _orderedListOEIndex
//     */
//    public int get_orderedListOEIndex() {
//        return _orderedListOEIndex;
//    }
//
//    /**
//     * ICE specific
//     * 
//     * @param listOEIndex
//     *            the _orderedListOEIndex to set
//     */
//    public void set_orderedListOEIndex(int listOEIndex) {
//        _orderedListOEIndex = listOEIndex;
//    }
//
//    /**
//     * ICE specific
//     * 
//     * @return the _orderedListOEAssocName
//     */
//    public String get_orderedListOEAssocName() {
//        return _orderedListOEAssocName;
//    }
//
//    /**
//     * ICE specific
//     * 
//     * @param listOEAssocName
//     *            the _orderedListOEAssocName to set
//     */
//    public void set_orderedListOEAssocName(String listOEAssocName) {
//        _orderedListOEAssocName = listOEAssocName;
//    }
//
//    /**
//     * ICE specific
//     * 
//     * @return the _creationOrderIndex
//     */
//    public int get_creationOrderIndex() {
//        return _creationOrderIndex;
//    }
//
//    /**
//     * ICE specific
//     * 
//     * @param orderIndex
//     *            the _creationOrderIndex to set
//     */
//    public void set_creationOrderIndex(int orderIndex) {
//        _creationOrderIndex = orderIndex;
//    }
//
//    /**
//     * Boolean for APIC-EM to identify that the contents of this instance are being mutated.
//     */
//    public boolean get_isBeingChanged() {
//        return _isBeingChanged;
//    }
//
//    /**
//     * Boolean for APIC-EM to identify that the contents of this instance are being mutated.
//     */
//    public void set_isBeingChanged(boolean _isBeingChanged) {
//        this._isBeingChanged = _isBeingChanged;
//    }
//    
//    public void setDeployPending(DeployPendingEnum e){
//        this.deployPending = e;
//    }
//    
//    public DeployPendingEnum getDeployPending(){
//        return this.deployPending;
//    }
//
//}
