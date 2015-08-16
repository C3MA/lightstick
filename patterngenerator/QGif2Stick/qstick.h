#ifndef QSTICK_H
#define QSTICK_H

#include <QObject>

class QColor;

class QStick : public QObject
{
    Q_OBJECT
    QByteArray ledBuffer;

public:
    explicit QStick(QObject *parent = 0);

    void    send2Stick(QString ip, u_int8_t stickNumber);
    void    setLED(int offset, QColor ledColor);
signals:

public slots:

};

#endif // QSTICK_H
