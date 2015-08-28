#include <QCoreApplication>

#include <QDebug>
#include <QImage>
#include <QColor>
#include <QPixmap>
#include <QPainter>
#include <QStaticText>

#define STICK_HEIGHT    60
#define DEFAULT_WIDTH   10

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    int x, y;
    QColor pixel;

    qDebug() << "Generate Image";
    QImage tmp(DEFAULT_WIDTH, STICK_HEIGHT, QImage::Format_RGB888);
    tmp.fill(qRgb(0,0,0));
    qDebug() << "Generate String";

    QPixmap px = QPixmap::fromImage(tmp);
    QPainter p (&px);
    p.setPen (Qt::white);

    QStaticText text;
    text.setText("Hello World");
    p.drawStaticText(3,10, text);
    p.end ();
    qDebug() << "String generated...";

    /* Debug Printing */
    for (x=0; x < DEFAULT_WIDTH; x++)
    {
        for (y=0; y < STICK_HEIGHT; y++)
        {
            pixel = QColor(tmp.pixel(x, y));
            qDebug() << x << "x" << y << " " << pixel.red() << pixel.green() << pixel.blue();
        }
    }

    return a.exec();
}
