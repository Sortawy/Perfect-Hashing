import pandas as pd
import matplotlib.pyplot as plt

data = {
    "Array Size": [10, 50, 100],
    "Linear Hashing": [0.531, 1.834, 4.620],
    "Quadratic Hashing": [0.110, 0.131, 0.247]
}

df = pd.DataFrame(data)

plt.figure(figsize=(10, 6))

plt.plot(df["Array Size"], df["Linear Hashing"], label="Linear Hashing", color="blue")
plt.plot(df["Array Size"], df["Quadratic Hashing"], label="Quadratic Hashing", color="green")

plt.title("Hashing Algorithms Performance")
plt.xlabel("Array Size")
plt.ylabel("Time (milliseconds)")
plt.grid(True)
plt.legend()

plt.show()

# Linear Hashing
plt.figure(figsize=(10, 6))
plt.plot(df["Array Size"], df["Linear Hashing"], label="Linear Hashing", color="blue")
plt.title("Linear Hashing")
plt.xlabel("Array Size")
plt.ylabel("Time (milliseconds)")
plt.grid(True)
plt.legend()
plt.show()

# Quadratic Hashing
plt.figure(figsize=(10, 6))
plt.plot(df["Array Size"], df["Quadratic Hashing"], label="Quadratic Hashing", color="green")
plt.title("Quadratic Hashing")
plt.xlabel("Array Size")
plt.ylabel("Time (milliseconds)")
plt.grid(True)
plt.legend()
plt.show()

fig, axs = plt.subplots(2, 1, figsize=(10, 10), gridspec_kw={'hspace': 0.4})

# Linear Hashing
axs[0].plot(df["Array Size"], df["Linear Hashing"], label="Linear Hashing", color="blue")
axs[0].set_title("Linear Hashing")
axs[0].set_xlabel("Array Size")
axs[0].set_ylabel("Time (milliseconds)")
axs[0].grid(True)

# Quadratic Hashing
axs[1].plot(df["Array Size"], df["Quadratic Hashing"], label="Quadratic Hashing", color="green")
axs[1].set_title("Quadratic Hashing")
axs[1].set_xlabel("Array Size")
axs[1].set_ylabel("Time (milliseconds)")
axs[1].grid(True)

plt.show()