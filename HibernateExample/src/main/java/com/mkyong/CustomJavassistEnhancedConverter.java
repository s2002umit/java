package com.mkyong;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Converter which deals with Javassist proxy's serialization/deserialization. While serializing, it
 * will convert the proxy into its actual object and put its properties into the xml string. By
 * default, xstream won't serialize the javassist proxy's properties in the serialized xml string, but
 * just puts a few information in serialized xml string which describe the interfaces, packages and
 * other information of current class, so we need this own-defined "CustomJavassistEnhancedConverter"
 * for javassist proxies.
 */
public class CustomJavassistEnhancedConverter implements Converter {
	
	private Converter defaultConverter;
	
	private Mapper mapper;
	
	/**
	 * @param mapper
	 * @param converterLookup
	 */
	public CustomJavassistEnhancedConverter(Mapper mapper, ConverterLookup converterLookup) {
		this.mapper = mapper;
		defaultConverter = converterLookup.lookupConverterForType(Object.class);
	}
	
	/**
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		/*
		 * through "SerializationMethodInvoker"'s callWriteReplace method, we
		 * can get the actual type of any proxy
		 */
		SerializationMethodInvoker serializationMethodInvoker = new SerializationMethodInvoker();
		Object newObj = (Object) serializationMethodInvoker.callWriteReplace(obj);
		
		/*
		 * if the proxy represents a sub class, it will give as
		 * follows: We can suppose this proxy represents one instance of
		 * User.class and User.class is a sub class of Person.class, then call
		 * this proxy's getClass().getSuperclass() will return "Person.class" so
		 * we need add a attribute "resolves-to" into the serialized xml string,
		 * then while deserializing, xstream can know the exact type of the
		 * deserialized class.
		 */
		if (!newObj.getClass().equals(obj.getClass().getSuperclass())) {
			/*
			 * add "resolves-to" attribute into element, so that while
			 * deserializing, xstream can know the actual class through
			 * "resolves-to"
			 */
			// String attributeName =
			// mapper.aliasForSystemAttribute("resolves-to");
			String attributeName = mapper.aliasForAttribute("resolves-to");
			if (attributeName != null) {
				String actualClassName = this.mapper.serializedClass(newObj.getClass());
				writer.addAttribute(attributeName, actualClassName);
			}
		}
		defaultConverter.marshal(newObj, writer, context);
	}
	
	/**
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        System.err.println("**** UNMARSHAL **** " + context.getRequiredType());
		return null;
	}
	
	/**
	 * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class type) {
		return type.getName().indexOf(JavassistMapper.OLD_NAMING_MARKER) > 0
        || type.getName().indexOf(JavassistMapper.NEW_NAMING_MARKER) > 0;
	}
	
}