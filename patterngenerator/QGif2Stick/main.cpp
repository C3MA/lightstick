#include <QApplication>
#include <QDebug>
#include <QMovie>
#include <QTime>
#include <QRgb>
#include "qstick.h"
#include <QColor>

QT_USE_NAMESPACE

static void delay(int milliseconds)
{
    QTime dieTime= QTime::currentTime().addMSecs(milliseconds);
    while (QTime::currentTime() < dieTime)
        QCoreApplication::processEvents(QEventLoop::AllEvents, milliseconds);
}


int main(int argc, char *argv[])
{
    int x, y;
    QApplication a(argc, argv);

    QStick stick;

    int argumentCount = QApplication::arguments().size();
    QStringList argumentList = QApplication::arguments();

    QTextStream standardOutput(stdout);
    if (argumentCount == 1) {
        standardOutput << QObject::tr("Usage: %1 <Gif file>").arg(argumentList.first()) << endl;
        return 1;
    }

    QString gifName = argumentList.at(1);
    QString ip /*= argumentList.at(2)*/;
    QMovie *movie = new QMovie();
    movie->setCacheMode(QMovie::CacheAll);
    movie->setFileName(gifName);
    movie->start();
    if (! movie->isValid()) {
        standardOutput << QObject::tr("The video file is corrupted") << endl;
        return 1;
    }

    while(movie->jumpToNextFrame())
    {
        qDebug() << "Frame number " << movie->currentFrameNumber();
        qDebug() << "Delay " << movie->nextFrameDelay();
        QImage img = movie->currentImage();
        qDebug() << img.width() << "x" << img.height();
        for(x=0; x < img.width(); x++)
        {
            for(y=0; y < img.height(); y++)
            {
                QRgb p = img.pixel(x,y);
                stick.setLED(y,QColor(p));
            }
            stick.send2Stick(ip, x + 1);
        }
        delay( movie->speed() );
    }

    return a.exec();
}
