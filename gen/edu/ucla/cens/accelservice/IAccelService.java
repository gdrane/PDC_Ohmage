/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/gaureshrane/NDN/PDC_Ohmage/src/edu/ucla/cens/accelservice/IAccelService.aidl
 */
package edu.ucla.cens.accelservice;
/* Goes with AccelService-v2.2.apk */
public interface IAccelService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.ucla.cens.accelservice.IAccelService
{
private static final java.lang.String DESCRIPTOR = "edu.ucla.cens.accelservice.IAccelService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.ucla.cens.accelservice.IAccelService interface,
 * generating a proxy if needed.
 */
public static edu.ucla.cens.accelservice.IAccelService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.ucla.cens.accelservice.IAccelService))) {
return ((edu.ucla.cens.accelservice.IAccelService)iin);
}
return new edu.ucla.cens.accelservice.IAccelService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_isRunning:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isRunning();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_start:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.start(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.stop(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_suggestRate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.suggestRate(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setReadingLength:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _arg1;
_arg1 = data.readLong();
long _result = this.setReadingLength(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_setWarmupLength:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _arg1;
_arg1 = data.readLong();
long _result = this.setWarmupLength(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_suggestInterval:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _arg1;
_arg1 = data.readLong();
long _result = this.suggestInterval(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getInterval:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getInterval();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getRate:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getRate();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getReadingLength:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getReadingLength();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getWarmupLength:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getWarmupLength();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getLastForce:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.getLastForce();
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_getLastXValues:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.getLastXValues();
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_getLastYValues:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.getLastYValues();
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_getLastZValues:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.getLastZValues();
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_getLastTimeStamp:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getLastTimeStamp();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.ucla.cens.accelservice.IAccelService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Returns true if the service is running.
     * 
     * @return                  current state of the service
     */
public boolean isRunning() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isRunning, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Starts the accelerometer service.
     *
     * @param   callerName      String identifying the client
	 */
public void start(java.lang.String callerName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
mRemote.transact(Stub.TRANSACTION_start, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * Stops the accelerometer service to save maximum power.
     *
     * @param   callerName      String identifying the client
	 */
public void stop(java.lang.String callerName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * Set the rate of accelerometer sampling. This is only a 
	 * suggestion and the service may choose a lower rate to save power. 
	 * Possible values are:
	 * SENSOR_DELAY_FASTEST, SENSOR_DELAY_GAME, 
     * SENSOR_DELAY_NORMA, SENSOR_DELAY_UI
	 * 
	 * @param 	rate	rate of sensor reading
     * @param   callerName      String identifying the client
	 * @return 			the actual rate that was set
	 */
public int suggestRate(java.lang.String callerName, int rate) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
_data.writeInt(rate);
mRemote.transact(Stub.TRANSACTION_suggestRate, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Set the length of the interval that accelerometer is recorded 
	 * before it is turned of (for duty-cycling).
	 *
	 * @param 	length		length of the interval for sensor 
     *                      reading in milliseconds
     * @param   callerName      String identifying the client
	 */
public long setReadingLength(java.lang.String callerName, long length) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
_data.writeLong(length);
mRemote.transact(Stub.TRANSACTION_setReadingLength, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Set the length of the warm-up interval before the actual
	 * reading interval begins.
	 *
	 * @param 	length		length of the warm-up interval for
	 *                      preparing the accelerometer 
     * @param   callerName      String identifying the client
     *
	 * @return				the new reading length
	 */
public long setWarmupLength(java.lang.String callerName, long length) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
_data.writeLong(length);
mRemote.transact(Stub.TRANSACTION_setWarmupLength, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Suggest length of the duty-cycling interval. The accelerometer sensor
	 * will be turned off for some time between readings.  This is only a 
	 * suggestion and the service may choose a longer interval to save power
	 * 
	 * @param	interval	suggested length of off interval in milliseconds
     * @param   callerName      String identifying the client
	 * @return				the actual interval in milliseconds
	 */
public long suggestInterval(java.lang.String callerName, long interval) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callerName);
_data.writeLong(interval);
mRemote.transact(Stub.TRANSACTION_suggestInterval, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the current sleeping interval.
	 * 
	 * @return				current sleep interval used by the 
     *                      service in milliseconds
	 */
public long getInterval() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInterval, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the current rate.
	 * 
	 * @return				current rate
	 */
public int getRate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRate, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the current reading length
	 * 
	 * @return				current reading length
	 */
public long getReadingLength() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getReadingLength, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the current length of the warm-up interval
	 * 
	 * @return				current warm-up interval length
	 */
public long getWarmupLength() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWarmupLength, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the latest recorded force vector.
	 * 
	 * @return				latest recorded force vector
	 */
public java.util.List getLastForce() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastForce, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the list of latest recorded X values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastXValues() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastXValues, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the list of latest recorded Y values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastYValues() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastYValues, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the list of latest recorded Z values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastZValues() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastZValues, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Returns the time-stamp of the last recorded value.
	 * This method can be used to verify the freshness of the values.
	 *
	 * @return 			time-stamp of the latest recorded sensor 
     *                    value in milliseconds
	 */
public long getLastTimeStamp() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastTimeStamp, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_isRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_start = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_suggestRate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setReadingLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setWarmupLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_suggestInterval = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getInterval = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getRate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getReadingLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getWarmupLength = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getLastForce = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getLastXValues = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getLastYValues = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getLastZValues = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getLastTimeStamp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
}
/**
     * Returns true if the service is running.
     * 
     * @return                  current state of the service
     */
public boolean isRunning() throws android.os.RemoteException;
/**
	 * Starts the accelerometer service.
     *
     * @param   callerName      String identifying the client
	 */
public void start(java.lang.String callerName) throws android.os.RemoteException;
/**
	 * Stops the accelerometer service to save maximum power.
     *
     * @param   callerName      String identifying the client
	 */
public void stop(java.lang.String callerName) throws android.os.RemoteException;
/**
	 * Set the rate of accelerometer sampling. This is only a 
	 * suggestion and the service may choose a lower rate to save power. 
	 * Possible values are:
	 * SENSOR_DELAY_FASTEST, SENSOR_DELAY_GAME, 
     * SENSOR_DELAY_NORMA, SENSOR_DELAY_UI
	 * 
	 * @param 	rate	rate of sensor reading
     * @param   callerName      String identifying the client
	 * @return 			the actual rate that was set
	 */
public int suggestRate(java.lang.String callerName, int rate) throws android.os.RemoteException;
/**
	 * Set the length of the interval that accelerometer is recorded 
	 * before it is turned of (for duty-cycling).
	 *
	 * @param 	length		length of the interval for sensor 
     *                      reading in milliseconds
     * @param   callerName      String identifying the client
	 */
public long setReadingLength(java.lang.String callerName, long length) throws android.os.RemoteException;
/**
	 * Set the length of the warm-up interval before the actual
	 * reading interval begins.
	 *
	 * @param 	length		length of the warm-up interval for
	 *                      preparing the accelerometer 
     * @param   callerName      String identifying the client
     *
	 * @return				the new reading length
	 */
public long setWarmupLength(java.lang.String callerName, long length) throws android.os.RemoteException;
/**
	 * Suggest length of the duty-cycling interval. The accelerometer sensor
	 * will be turned off for some time between readings.  This is only a 
	 * suggestion and the service may choose a longer interval to save power
	 * 
	 * @param	interval	suggested length of off interval in milliseconds
     * @param   callerName      String identifying the client
	 * @return				the actual interval in milliseconds
	 */
public long suggestInterval(java.lang.String callerName, long interval) throws android.os.RemoteException;
/**
	 * Returns the current sleeping interval.
	 * 
	 * @return				current sleep interval used by the 
     *                      service in milliseconds
	 */
public long getInterval() throws android.os.RemoteException;
/**
	 * Returns the current rate.
	 * 
	 * @return				current rate
	 */
public int getRate() throws android.os.RemoteException;
/**
	 * Returns the current reading length
	 * 
	 * @return				current reading length
	 */
public long getReadingLength() throws android.os.RemoteException;
/**
	 * Returns the current length of the warm-up interval
	 * 
	 * @return				current warm-up interval length
	 */
public long getWarmupLength() throws android.os.RemoteException;
/**
	 * Returns the latest recorded force vector.
	 * 
	 * @return				latest recorded force vector
	 */
public java.util.List getLastForce() throws android.os.RemoteException;
/**
	 * Returns the list of latest recorded X values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastXValues() throws android.os.RemoteException;
/**
	 * Returns the list of latest recorded Y values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastYValues() throws android.os.RemoteException;
/**
	 * Returns the list of latest recorded Z values.
	 * Each element of the list contains an array of values.
	 *
	 * @return				latest recorded values
	 */
public java.util.List getLastZValues() throws android.os.RemoteException;
/**
	 * Returns the time-stamp of the last recorded value.
	 * This method can be used to verify the freshness of the values.
	 *
	 * @return 			time-stamp of the latest recorded sensor 
     *                    value in milliseconds
	 */
public long getLastTimeStamp() throws android.os.RemoteException;
}
