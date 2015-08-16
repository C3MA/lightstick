#include "qstick.h"
#include <QColor>
#include <QUdpSocket>

enum Constants {
    rgb_size = 3,
    rgb_led_max = 100, /* The LEDs must never leave this boundary, instead of 255 (rgb_range_maximum) */
    rgb_range_maximum = 255,
};

QStick::QStick(QObject *parent) :
    QObject(parent)
{

}

void QStick::setLED(int offset, QColor ledColor)
{
    ledBuffer.insert(offset * rgb_size + 0, ledColor.red() * rgb_led_max / rgb_range_maximum);
    ledBuffer.insert(offset * rgb_size + 1, ledColor.green() * rgb_led_max / rgb_range_maximum);
    ledBuffer.insert(offset * rgb_size + 2, ledColor.blue() * rgb_led_max / rgb_range_maximum);
}


void QStick::send2Stick(QString ip, u_int8_t stickNumber) {
    QUdpSocket txSocket(this); //create UDP socket to send to
    QByteArray outputBuffer;
    outputBuffer.append(stickNumber);
    outputBuffer.append("\0\0\0", 3);
    outputBuffer.append(ledBuffer);
    //Send the datagram
    txSocket.writeDatagram(outputBuffer.data(), outputBuffer.size(), QHostAddress(QString("192.168.23.%1").arg(stickNumber)), 2342);
    txSocket.flush();
    txSocket.close();

    // aufräumen
    ledBuffer.clear();
}

