import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

def main():
    def read(file):
        df = pd.read_csv(file)
        return df.values

    X = read("/home/divyakshi/Documents/2000x.csv")
    Y = read("/home/divyakshi/Documents/2000y.csv")

    X = X.reshape((X.shape[0],))
    Y = Y.reshape((Y.shape[0],))

    '''X=(X-X.mean())/(X.std())
    Y=(Y-Y.mean())/(Y.std())'''
    plt.scatter(X, Y, label="Requested Point")

    plt.grid(True)
    plt.title("DELHI\nRequest Location")
    plt.xlabel("Longitude")
    plt.ylabel("Latitude")
    plt.legend()
    plt.show()
    mx = np.max(X)
    minx = np.min(X)
    my = np.max(Y)
    miny = np.min(Y)
    deltax = (mx - minx) / 8
    deltay = (my - miny) / 12
    print(deltax)
    print(deltay)
    Ax = []
    Ay = []
    c = 0
    initialx = minx + deltax
    fx = minx
    W = []
    while initialx <= mx + deltax:
        fy = miny
        initialy = miny + deltay
        while initialy <= my + deltay:
            By = []
            Bx = []
            w = 0
            for j in range(X.shape[0]):
                if initialx > X[j] >= fx and initialy > Y[j] >= fy:
                    By.append((Y[j]))
                    Bx.append((X[j]))
                    w += 1
            Ay.append(By)
            Ax.append(Bx)
            if w != 0:
                W.append(w)
            c += 1
            fy = initialy
            initialy += deltay
        fx = initialx
        initialx += deltax
    print(Ax)
    print(Ay)

    def average(Ax):
        Avx = []
        for x in Ax:
            if x != []:
                x = np.asarray(x)
                Avx.append(np.mean(x))
        return Avx

    Avx = average(Ax)
    Avy = average(Ay)
    print(Avx)
    print(Avy)
    print(W)
    Wmax = max(W)
    Wmin = min(W)
    tot = Wmax - Wmin
    HighX = []
    ModerateX = []
    LowX = []
    MinimalX = []
    HighY = []
    ModerateY = []
    LowY = []
    MinimalY = []
    for i in range(len(W)):
        if W[i] <= (Wmin + tot / 2):
            MinimalX.append(Avx[i])
            MinimalY.append(Avy[i])
        elif W[i] <= (Wmin + 2 * tot / 3):
            LowX.append(Avx[i])
            LowY.append(Avy[i])
            # plt.scatter(Avx[i], Avy[i], c='yellow')
        elif W[i] <= (Wmin + 8 * tot / 10):
            ModerateX.append(Avx[i])
            ModerateY.append(Avy[i])
        else:
            HighX.append(Avx[i])
            HighY.append(Avy[i])
            # plt.scatter(Avx[i], Avy[i], c='red')
    plt.scatter(HighX, HighY, color="Red", label="High")
    plt.scatter(MinimalX, MinimalY, color="Green", label="Minimal")
    plt.scatter(LowX, LowY, color="Yellow", label="Low")
    plt.scatter(ModerateX, ModerateY, color="Orange", label="Moderate")
    plt.xlabel("Longitude")
    plt.ylabel("Latitude")
    plt.title("DELHI\nClustered Request Frequency\nBus Routes")
    plt.legend()
    plt.grid(True)
    plt.show()

    plt.scatter(HighX, HighY, color="Red", label="High")
    plt.scatter(MinimalX, MinimalY, color="Green", label="Minimal")
    plt.scatter(LowX, LowY, color="Yellow", label="Low")
    plt.scatter(ModerateX, ModerateY, color="Orange", label="Moderate")
    plt.plot(HighX, HighY, color="Red", label="Route", linestyle="--")
    plt.xlabel("Longitude")
    plt.ylabel("Latitude")
    plt.title("DELHI\nClustered Request Frequency\nBus Routes")
    plt.legend()
    plt.grid(True)
    plt.show()
    print(c)
    print(sum(W))
    print(mx - minx)
    print(my - miny)
    print(HighX)
    print(HighY)

if __name__ == "__main__":
    main()