/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.test.roaster.model;

import java.util.List;
import java.util.Map;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class MethodReturnTypeTest
{
   @Test
   public void testGetReturnTypeReturnsFullTypeForJavaLang()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod("public Long getLong()");
      assertEquals("java.lang.Long", method.getReturnType().getQualifiedName());
   }

   @Test
   public void testGetReturnTypeReturnsFullTypeForJavaLangGeneric()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List<Long> getLong(return null;)");
      method.getOrigin().addImport(List.class);
      assertEquals("java.util.List", method.getReturnType().getQualifiedName());
      assertEquals("java.util.List<Long>", method.getReturnType().getQualifiedNameWithGenerics());
   }

   @Test
   public void testGetQualifiedReturnTypePrimitiveArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod(
               "public long[] getLongArray()");
      assertEquals("long", method.getReturnType().getQualifiedName());
      assertTrue(method.getReturnType().isArray());
      assertEquals(1, method.getReturnType().getArrayDimensions());
   }

   @Test
   public void testGetQualifiedReturnTypeObjectArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod(
               "public Long[] getLongArray()");
      assertEquals("java.lang.Long", method.getReturnType().getQualifiedName());
      assertTrue(method.getReturnType().isArray());
      assertEquals(1, method.getReturnType().getArrayDimensions());
   }

   @Test
   public void testGetQualifiedReturnTypeNDimensionObjectArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod(
               "public Long[][] getLongArray()");
      assertEquals("java.lang.Long", method.getReturnType().getQualifiedName());
      assertTrue(method.getReturnType().isArray());
      assertEquals(2, method.getReturnType().getArrayDimensions());
   }

   @Test
   public void testGetQualifiedReturnTypeObjectArrayOfImportedType()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod(
               "public List[] getListArray()");
      method.getOrigin().addImport(List.class);
      assertEquals("java.util.List", method.getReturnType().getQualifiedName());
      assertTrue(method.getReturnType().isArray());
      assertEquals(1, method.getReturnType().getArrayDimensions());
   }

   @Test
   public void testGetQualifiedReturnTypeImportedObjectArrayParameterizedImportedType()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class).addMethod(
               "public List<Long>[] getListArray()");
      method.getOrigin().addImport(List.class);
      assertEquals("java.util.List", method.getReturnType().getQualifiedName());
      assertTrue(method.getReturnType().isArray());
      assertEquals(1, method.getReturnType().getArrayDimensions());
   }

   @Test
   public void testGetReturnTypePrimitiveObjectArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public long[] getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("long", type.getQualifiedName());
      assertFalse(type.isParameterized());
      assertFalse(type.isWildcard());
      assertTrue(type.isPrimitive());
      assertFalse(type.isQualified());
      assertTrue(type.isArray());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(0, arguments.size());
   }

   @Test
   public void testGetReturnTypeBoxedObjectArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public Long[] getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.lang.Long", type.getQualifiedName());
      assertFalse(type.isParameterized());
      assertFalse(type.isWildcard());
      assertFalse(type.isPrimitive());
      assertFalse(type.isQualified());
      assertTrue(type.isArray());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(0, arguments.size());
   }

   @Test
   public void testGetReturnTypeObjectArray()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List[] getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.List", type.getQualifiedName());
      assertFalse(type.isParameterized());
      assertFalse(type.isWildcard());
      assertFalse(type.isPrimitive());
      assertFalse(type.isQualified());
      assertTrue(type.isArray());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(0, arguments.size());
   }

   @Test
   public void testGetReturnTypeObjectArrayParameterized()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List<Long>[] getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.List", type.getQualifiedName());
      assertTrue(type.isParameterized());
      assertFalse(type.isWildcard());
      assertFalse(type.isPrimitive());
      assertFalse(type.isQualified());
      assertTrue(type.isArray());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(1, arguments.size());
   }

   @Test
   public void testGetReturnTypeObjectUnparameterized()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List getLong(return null;)");
      method.getOrigin().addImport(List.class);
      assertEquals("java.util.List", method.getReturnType().getQualifiedName());
      assertFalse(method.getReturnType().isParameterized());
   }

   @Test
   public void testGetReturnTypeObjectParameterized()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List<Long> getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.List", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(1, arguments.size());
      assertEquals("Long", arguments.get(0).getName());
      assertEquals("java.lang.Long", arguments.get(0).getQualifiedName());
   }

   @Test
   public void testGetReturnTypeObjectWildcard()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List<?> getList(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.List", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(1, arguments.size());
      assertEquals("?", arguments.get(0).getName());
      assertEquals("?", arguments.get(0).getQualifiedName());
   }

   @Test
   public void testGetReturnTypeObjectParameterizedMultiple()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public Map<String, Long> getMap(return null;)");
      method.getOrigin().addImport(Map.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.Map", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(2, arguments.size());
      assertEquals("String", arguments.get(0).getName());
      assertEquals("java.lang.String", arguments.get(0).getQualifiedName());

      assertEquals("Long", arguments.get(1).getName());
      assertEquals("java.lang.Long", arguments.get(1).getQualifiedName());
   }

   @Test
   public void testGetReturnTypeObjectParameterizedNested()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public List<List<Long>> getLists(return null;)");
      method.getOrigin().addImport(List.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.List", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(1, arguments.size());
      assertEquals("List", arguments.get(0).getName());
      assertEquals("java.util.List", arguments.get(0).getQualifiedName());

      assertEquals(1, arguments.size());
      assertEquals("Long", arguments.get(0).getTypeArguments().get(0).getName());
      assertEquals("java.lang.Long", arguments.get(0).getTypeArguments().get(0).getQualifiedName());
   }

   @Test
   public void testGetReturnTypeObjectParameterizedMultipleNested()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public Map<String, List<Long>> getMap(return null;)");
      method.getOrigin().addImport(List.class);
      method.getOrigin().addImport(Map.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.Map", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(2, arguments.size());
      assertEquals("String", arguments.get(0).getName());
      assertEquals("java.lang.String", arguments.get(0).getQualifiedName());

      assertEquals("List", arguments.get(1).getName());
      assertEquals("java.util.List", arguments.get(1).getQualifiedName());
   }

   @Test
   public void testGetReturnTypeObjectParameterizedArrayMultipleNested()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public Map<String, List<Long>>[] getMaps(return null;)");
      method.getOrigin().addImport(List.class);
      method.getOrigin().addImport(Map.class);
      Type<JavaClassSource> type = method.getReturnType();
      assertEquals("java.util.Map", type.getQualifiedName());
      assertTrue(type.isParameterized());

      List<Type<JavaClassSource>> arguments = type.getTypeArguments();

      assertEquals(2, arguments.size());
      assertEquals("String", arguments.get(0).getName());
      assertEquals("java.lang.String", arguments.get(0).getQualifiedName());

      assertEquals("List", arguments.get(1).getName());
      assertEquals("java.util.List", arguments.get(1).getQualifiedName());
   }

   @Test
   public void testReturnTypeIsVoidForConstructors()
   {
      MethodSource<JavaClassSource> method = Roaster.create(JavaClassSource.class)
               .addMethod("public Constructor(){}").setConstructor(true);
      assertTrue(method.isConstructor());
      assertTrue(method.isReturnTypeVoid());
   }

   @Test
   public void testSetReturnTypeShouldImportClass()
   {
      final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);

      javaClassSource.setPackage("org.agoncal.myproj").setName("MyEndpoint").addAnnotation(Deprecated.class)
               .setStringValue("/mypath");
      MethodSource<?> doGet = javaClassSource.addMethod().setPublic().setName("method")
               .setReturnType(List.class);
      doGet.setBody("return null;");
      assertTrue(javaClassSource.hasImport("java.util.List"));
      assertTrue(javaClassSource.hasImport(List.class));
   }

   @Test
   public void testReturnTypeShouldKeepGenerics()
   {
      JavaInterfaceSource ifSource = Roaster.create(JavaInterfaceSource.class);
      ifSource.setPackage("poc.test.bug");
      MethodSource<JavaInterfaceSource> method = ifSource.addMethod()
               .setReturnType("com.google.common.collect.ListMultimap<java.lang.Integer,poc.test.Test3>")
               .setName("getMap");
      assertTrue(ifSource.hasImport("com.google.common.collect.ListMultimap"),
               "com.google.common.collect.ListMultimap should have been imported");
      assertTrue(ifSource.hasImport("poc.test.Test3"), "Test3 should have been imported");
      assertEquals("com.google.common.collect.ListMultimap<Integer,Test3>",
               method.getReturnType().getQualifiedNameWithGenerics());
   }

   @Test
   public void testSpaceInTwoGenericTypesShouldNotThrowException()
   {
      JavaClassSource classSource = Roaster.create(JavaClassSource.class);
      MethodSource<JavaClassSource> newMethod = classSource.addMethod()
               .setName("tstMethod")
               .setPublic();
      newMethod.setReturnType(
               "java.util.TreeMap<java.util.String, java.util.Object>"); // Counts space as part of the second type's
      // name
      newMethod.setBody("return new TreeMap<String, Object>();");
      assertThat(classSource.toString()).contains("public TreeMap<String, Object> tstMethod()");
   }

   @Test
   public void testNestedTypedParametersShouldNotThrowException()
   {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class).setPackage("com.scratch")
               .setName("Example");
      final MethodSource<JavaClassSource> method = javaClass.addMethod().setName("createMap")
               .setReturnType("java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.String>>");
      assertEquals("java.util.Map<String,Map<String,String>>",
               method.getReturnType().getQualifiedNameWithGenerics());
   }
}